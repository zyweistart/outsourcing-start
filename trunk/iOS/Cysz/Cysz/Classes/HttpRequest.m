//
//  HttpRequest.m
//  ACyulu
//
//  Created by Start on 12-12-6.
//  Copyright (c) 2012年 ancun. All rights reserved.
//

#import "HttpRequest.h"
#import "GCDiscreetNotificationView.h"
#import "NSString+OAURLEncodingAdditions.h"
#import "BaseRefreshTableViewController.h"

@implementation HttpRequest

@synthesize controller;
@synthesize message;
@synthesize isVerify;
@synthesize isFileDownload;
@synthesize requestCode;
@synthesize propertys;
@synthesize delegate;

- (BOOL) isNetworkConnection{
    BOOL isExistenceNetwork = YES;
    Reachability *r = [Reachability reachabilityWithHostName:@"www.apple.com"];
    switch ([r currentReachabilityStatus]) {
        case NotReachable:
            //没有网络
            isExistenceNetwork=NO;
            break;
        case ReachableViaWWAN:
            //正在使用3G网络
            isExistenceNetwork=YES;
            break;
        case ReachableViaWiFi:
            //正在使用wifi网络
            isExistenceNetwork=YES;
            break;
        default:
            isExistenceNetwork=NO;
            break;
    }
    return isExistenceNetwork;
}

- (void) loginhandle:(NSString*)url requestParams:(NSMutableDictionary*)request {
    if([[Config Instance]isLogin]){
        [request setObject:[[[Config Instance] userInfo] objectForKey:@"accessid"] forKey:@"accessid"];
        [self handle:url signKey:[[[Config Instance] userInfo] objectForKey:@"accesskey"]  headParams:nil requestParams:request];
    }else{
        [Common noLoginAlert:self];
    }
}

- (void) handle:(NSString*)url signKey:(NSString*)signKey requestParams:(NSMutableDictionary*)request {
    [self handle:url signKey:signKey headParams:nil requestParams:request];
}

- (void) handle:(NSString*)action signKey:(NSString*)signKey headParams:(NSMutableDictionary*)head requestParams:(NSMutableDictionary*)request {
    if ([self isNetworkConnection]) {
        NSString *requestBodyContent=[XML generate:action requestParams:request];
        
        [action release];
        [request release];
        if(!head){
            head=[[NSMutableDictionary alloc]init];
        }
        if(![head objectForKey:@"sign"]){
            //签名
            if(signKey){
                [head setObject:[[Crypto md5:requestBodyContent] gh_HMACSHA1:signKey] forKey:@"sign"];
            }else{
                [head setObject:[Crypto md5:requestBodyContent] forKey:@"sign"];
            }
        }
        //请求长度
        [head setObject:[NSString stringWithFormat:@"%d",[[requestBodyContent dataUsingEncoding:NSUTF8StringEncoding] length]] forKey:@"reqlength"];
        ASIHTTPRequest *asihttp = [ASIHTTPRequest requestWithURL:[NSURL URLWithString:ANCUN_HTTP_URL]];
        
        //设置请求头参数
        for(NSString *key in head){
            //URL编码
            [asihttp addRequestHeader:key value:[[head objectForKey:key] URLEncodedString]];
            [key release];
        }
        
        [head release];
        
        [asihttp setRequestMethod:@"POST"];
        [asihttp appendPostData:[requestBodyContent dataUsingEncoding:NSUTF8StringEncoding]];
        [asihttp setDelegate:self];
        if(isFileDownload){
            proHud=[[ATMHud alloc]init];
            [self.controller.view addSubview:proHud.view];
            [asihttp setDownloadProgressDelegate:proHud];
            [asihttp setShowAccurateProgress:YES];
            [proHud setCaption:@"下载中，请稍候"];
            [proHud setProgress:1];
            [proHud show];
            [[Config Instance]setIsCalculateTotal:YES];
        }else{
            if(self.controller){
                asihttp.hud = [[MBProgressHUD alloc] initWithView:self.controller.view];
                [self.controller.view addSubview:asihttp.hud];
                if(self.message){
                    asihttp.hud.labelText = message;
                }
                asihttp.hud.dimBackground = YES;
                asihttp.hud.square = YES;
                [asihttp.hud show:YES];
            }
        }
        [asihttp startAsynchronous];
        [requestBodyContent release];
    }else{
        if(self.controller){
            [Common notificationMessage:@"网络连接出错，请检测网络设置" inView:self.controller.view];
            if( [delegate respondsToSelector: @selector(requestFailed:requestCode:)]){
                [delegate requestFailed:nil requestCode:self.requestCode];
            }
        }
    }
}

- (void) requestFinished:(ASIHTTPRequest *)request { 
    if( [delegate respondsToSelector: @selector(requestFinished:requestCode:)]){
        [delegate requestFinished:request requestCode:self.requestCode];
    }else if( [delegate respondsToSelector: @selector(requestFinishedByResponse:requestCode:)]){
        Response *response=[[Response alloc]init];
        [response setPropertys:propertys];
        [response setRequest:request];
        if(!isFileDownload){
            NSString *responseString = [request responseString];
            response=[XML analysis:responseString];
            [response setResponseString:responseString];
            if(!isVerify){
                
                [response setSuccessFlag:NO];
                if([[response code] isEqualToString:@"100000"]){
                    [response setSuccessFlag:YES];
                }else if([[response code] isEqualToString:@"110042"]){
                    //暂无记录
                    if([self.controller isKindOfClass:[BaseRefreshTableViewController class]]){
                        [[((BaseRefreshTableViewController*)self.controller) dataItemArray]removeAllObjects];
                        [[((BaseRefreshTableViewController*)self.controller) tableView]reloadData];
                    }
                    [response setSuccessFlag:YES];
                    if(self.controller){
                        [Common notificationMessage:[response msg] inView:self.controller.view];
                    }
                }else if([[response code] isEqualToString:@"110026"]){
                    //通行证编号错误或未登录
                    [[Config Instance]setIsLogin:NO];
                    [Common noLoginAlert:self];
                }else if([[response code] isEqualToString:@"110036"]){
                    //签名不匹配或密码不正确
                    [Common alert:@"密码有误"];
                }else{
                    [Common alert:[response msg]];
                }
            }
        }else{
            
        }
        [delegate requestFinishedByResponse:response requestCode:self.requestCode];
        [response release];
    }
    if (request.hud) {
        [request.hud hide:YES];
    }
    if(proHud){
        [proHud hide];
    }
}

- (void) requestFailed:(ASIHTTPRequest *)request {
    if( [delegate respondsToSelector: @selector(requestFailed:requestCode:)]){
        [delegate requestFailed:request requestCode:self.requestCode];
    }else{
        [Common alert:@"请求出现异常"];
    }
    if (request.hud) {
        [request.hud hide:YES];
    }
    if(proHud){
        [proHud hide];
    }
}

- (void) actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex {
    if(buttonIndex==0){
        [Common resultLoginViewController:self.controller resultCode:1 requestCode:0 data:nil];
    }
}

- (void) dealloc {
    [message release];
    [controller release];
    [propertys release];
    [delegate release];
    [super dealloc];
}

@end

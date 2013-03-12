//
//  Common.m
//  ACyulu
//
//  Created by Start on 13-1-5.
//  Copyright (c) 2013年 ancun. All rights reserved.
//

#import "Common.h"
#import "GCDiscreetNotificationView.h"

@implementation Common
//设置缓存内容
+ (void) setCacheByBool:(NSString *)key data:(BOOL)data{
    NSUserDefaults * setting = [NSUserDefaults standardUserDefaults];
    [setting setBool:data forKey:key];
    [setting synchronize];
}
//设置缓存内容
+ (void) setCache:(NSString *)key data:(id)data{
    NSUserDefaults * setting = [NSUserDefaults standardUserDefaults];
    [setting setObject:data forKey:key];
    [setting synchronize];
}
+ (BOOL) getCacheByBool:(NSString *)key{
    NSUserDefaults * settings = [NSUserDefaults standardUserDefaults];
    return [settings boolForKey:key];
}
//获取缓存数据ByNSMutableArray
+ (id) getCache:(NSString *)key{
    NSUserDefaults * settings = [NSUserDefaults standardUserDefaults];
    return [settings objectForKey:key];
}
//提示信息框
+ (void) alert:(NSString*)message
{
    UIAlertView *alert = [[UIAlertView alloc]
                          initWithTitle:@"信息"
                          message:message
                          delegate:nil
                          cancelButtonTitle:@"确定"
                          otherButtonTitles:nil, nil];
    [alert show];
    [alert release];
}

+ (void) notificationMessage:(NSString *)message inView:(UIView*)aView{
    GCDiscreetNotificationView *notificationView = [[GCDiscreetNotificationView alloc] initWithText:message showActivity:NO inPresentationMode:GCDiscreetNotificationViewPresentationModeTop inView:aView];
    [notificationView show:YES];
    [notificationView hideAnimatedAfter:2.6];
}

+ (void) actionSheet:(id<UIActionSheetDelegate>)delegate message:(NSString*)message tag:(NSInteger)tag
{
    UIActionSheet *sheet = [[UIActionSheet alloc]
                            initWithTitle:message
                            delegate:delegate
                            cancelButtonTitle:@"取消"
                            destructiveButtonTitle:@"确定"
                            otherButtonTitles:nil,nil];
    sheet.tag=tag;
    //UIActionSheet与UITabBarController结合使用不能使用[sheet showInView:self.view];
    [sheet showInView:[UIApplication sharedApplication].keyWindow];
    [sheet release];
}
//没有登录时提示
+ (void) noLoginAlert:(id<UIActionSheetDelegate>)delegate
{
    UIActionSheet *sheet = [[UIActionSheet alloc]
                            initWithTitle:@"您还没有登录"
                            delegate:delegate
                            cancelButtonTitle:@"取消"
                            destructiveButtonTitle:@"登录"
                            otherButtonTitles:nil,nil];
    //UIActionSheet与UITabBarController结合使用不能使用[sheet showInView:self.view];
    [sheet showInView:[UIApplication sharedApplication].keyWindow];
    [sheet release];
}
//把秒转换成12时12分12秒中文形式
+ (NSString *) secondConvertFormatTimerByCn:(NSString*)second
{
    NSInteger sec=[second intValue];
    int day=sec/(60*60*24);
    int h=(sec%(60*60*24))/(60*60);
    int m=((sec%(60*60*24))%(60*60))/60;
    int s=(((sec%(60*60*24))%(60*60))%60)%60;
    if(day>0){
        return [NSString stringWithFormat:@"%d天%d时%d分%d秒",day,h,m,s];
    }else if(h>0){
        return [NSString stringWithFormat:@"%d时%d分%d秒",h,m,s];
    }else if(m>0){
        return [NSString stringWithFormat:@"%d分%d秒",m,s];
    }else{
        return [NSString stringWithFormat:@"%d秒",s];
    }
}
//把秒转换成12:12:12英文形式
+ (NSString *) secondConvertFormatTimerByEn:(NSString*)second
{
    NSInteger sec=[second intValue];
    int day=sec/(60*60*24);
    int h=(sec%(60*60*24))/(60*60);
    int m=((sec%(60*60*24))%(60*60))/60;
    int s=(((sec%(60*60*24))%(60*60))%60)%60;
    NSString *days=nil;
    NSString *hs=nil;
    NSString *ms=nil;
    NSString *ss=nil;
    if(day>=10){
        days=[NSString stringWithFormat:@"%d",day];
    }else{
        days=[NSString stringWithFormat:@"0%d",day];
    }
    if(h>=10){
        hs=[NSString stringWithFormat:@"%d",h];
    }else{
        hs=[NSString stringWithFormat:@"0%d",h];
    }
    if(m>=10){
        ms=[NSString stringWithFormat:@"%d",m];
    }else{
        ms=[NSString stringWithFormat:@"0%d",m];
    }
    if(s>=10){
        ss=[NSString stringWithFormat:@"%d",s];
    }else{
        ss=[NSString stringWithFormat:@"0%d",s];
    }
    if(day>0){
        return [NSString stringWithFormat:@"%@:%@:%@:%@",days,hs,ms,ss];
    }else if(h>0){
        return [NSString stringWithFormat:@"%@:%@:%@",hs,ms,ss];
    }else if(m>0){
        return [NSString stringWithFormat:@"%@:%@",ms,ss];
    }else{
        return [NSString stringWithFormat:@"00:%@",ss];
    }
}
//返回登录界面
+ (void) resultLoginViewController:(UIViewController*) view resultCode:(NSInteger)resultCode requestCode:(NSInteger)requestCode data:(NSMutableDictionary *)result
{
    [Common resultViewController:view resultDelegate:[[Config Instance] loginResultDelegate] resultCode:resultCode requestCode:requestCode data:result];
}
//返回调用
+ (void) resultViewController:(UIViewController*) view resultDelegate:(NSObject<ResultDelegate>*)resultDelegate resultCode:(NSInteger)resultCode requestCode:(NSInteger)requestCode data:(NSMutableDictionary *)result
{
    if(resultDelegate){
        if( [resultDelegate respondsToSelector: @selector(onControllerResult:requestCode:data:)]){
            [resultDelegate onControllerResult:resultCode requestCode:requestCode data:result];
        }
    }
    [view dismissViewControllerAnimated:YES completion:nil];
}

+ (NSString*) formatPhone:(NSString*) phone{
    NSMutableString *phoneNumber=[[NSMutableString alloc]init];
    for(int i=0;i<[phone length];i++){
        unichar c=[phone characterAtIndex:i];
        if(c>=48&&c<=57){
            [phoneNumber appendFormat:@"%c",c];
        }
    }
    return [NSString stringWithFormat:@"%@",phoneNumber];
}

@end

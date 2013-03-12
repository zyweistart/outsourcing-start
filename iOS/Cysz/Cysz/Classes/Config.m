//
//  Config.m
//  ACyulu
//
//  Created by Start on 12-12-6.
//  Copyright (c) 2012年 ancun. All rights reserved.
//

#import "Config.h"

@implementation Config

static Config * instance = nil;
+ (Config *) Instance {
    @synchronized(self){
        if(nil == instance){
            instance=[self new];
            //禁止拨打的号码列表
            NSMutableArray* phoneList=[[NSMutableArray alloc]init];
            [phoneList addObject:@"95105856"];
            [phoneList addObject:@"110"];
            [instance setNoDialPhoneNumber:phoneList];
        }
    }
    return instance;
}

- (void) dealloc {
    [_noDialPhoneNumber release];
    [_contact release];
    [_cacheKey release];
    [_userInfo release];
    [super dealloc];
}

@end

//
//  ACResponse.m
//  ACyulu
//
//  Created by Start on 12-12-6.
//  Copyright (c) 2012年 ancun. All rights reserved.
//

#import "Response.h"

@implementation Response

@synthesize successFlag;

@synthesize propertys;

@synthesize request;

@synthesize responseString;

@synthesize code;

@synthesize msg;

@synthesize pageInfo;

@synthesize dataItemArray;

@synthesize mainData;

-(id)init
{
    self=[super init];
    if(self){
        successFlag=NO;
    }
    return self;
}

- (void) dealloc{
    [propertys release];
    [responseString release];
    [code release];
    [msg release];
    [pageInfo release];
//    [dataItemArray release];
    [mainData release];
    [super dealloc];
}

@end

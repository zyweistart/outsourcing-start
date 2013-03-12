//
//  ACResponse.h
//  ACyulu
//
//  Created by Start on 12-12-6.
//  Copyright (c) 2012年 ancun. All rights reserved.
//

@interface Response : NSObject

@property Boolean successFlag;

@property (retain,nonatomic) ASIHTTPRequest *request;

@property (retain,nonatomic) NSMutableDictionary *propertys;

@property (retain,nonatomic) NSString *responseString;

@property (retain,nonatomic) NSString *code;

@property (retain,nonatomic) NSString *msg;

@property (retain,nonatomic) NSMutableDictionary *pageInfo;

@property (retain,nonatomic) NSMutableArray *dataItemArray;

@property (retain,nonatomic) NSMutableDictionary *mainData;

@end
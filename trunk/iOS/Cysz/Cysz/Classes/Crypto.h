//
//  Crypto.h
//  ACyulu
//
//  Created by Start on 12-12-5.
//  Copyright (c) 2012年 ancun. All rights reserved.
//

#import <CommonCrypto/CommonDigest.h>

@interface Crypto : NSObject

+ (NSString *) md5:(NSString *)text;
+ (NSData*) base64Decode:(NSString *)string;
+ (NSString*) base64Encode:(NSData *)data;

@end

//
//  Common.h
//  ACyulu
//
//  Created by Start on 13-1-5.
//  Copyright (c) 2013年 ancun. All rights reserved.
//

#import <Foundation/Foundation.h>

@class UIActionSheetDelegate;
@interface Common : NSObject

+ (void) setCacheByBool:(NSString *)key data:(BOOL)data;

+ (void) setCache:(NSString *)key data:(id)data;

+ (BOOL) getCacheByBool:(NSString *)key;

+ (id) getCache:(NSString *)key;

+ (void) alert:(NSString*)message;

+ (void) notificationMessage:(NSString *)message inView:(UIView*)aView;

+ (void) actionSheet:(id<UIActionSheetDelegate>)delegate message:(NSString*)message tag:(NSInteger)tag;

+ (void) noLoginAlert:(id<UIActionSheetDelegate>) delegate;

+ (NSString *) secondConvertFormatTimerByCn:(NSString*)second;

+ (NSString *) secondConvertFormatTimerByEn:(NSString*)second;

+ (void) resultLoginViewController:(UIViewController*) view resultCode:(NSInteger)resultCode requestCode:(NSInteger)requestCode data:(NSMutableDictionary *)result;
+ (void) resultViewController:(UIViewController*) view resultDelegate:(NSObject<ResultDelegate>*)resultDelegate resultCode:(NSInteger)resultCode requestCode:(NSInteger)requestCode data:(NSMutableDictionary *)result;

+ (NSString*) getMyPhoneNumber;

+ (NSString*) formatPhone:(NSString*) phone;

@end

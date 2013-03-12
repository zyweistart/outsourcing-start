//
//  STAppDelegate.h
//  Cysz
//
//  Created by Start on 13-3-12.
//  Copyright (c) 2013年 Start. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface STAppDelegate : UIResponder <UIApplicationDelegate,UITabBarControllerDelegate>{
    int m_lastTabIndex;
}

@property (strong, nonatomic) UIWindow *window;

@property (strong, nonatomic) UITabBarController *tabBarController;

@end

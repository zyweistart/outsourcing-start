//
//  STAppDelegate.m
//  Cysz
//
//  Created by Start on 13-3-12.
//  Copyright (c) 2013年 Start. All rights reserved.
//

#import "STAppDelegate.h"

#import "STProductViewController.h"
#import "STTravelViewController.h"
#import "STMembershipCardViewController.h"
#import "STMemberViewController.h"
#import "STMoreViewController.h"

@implementation STAppDelegate

- (void)dealloc{
    [_tabBarController release];
    [_window release];
    [super dealloc];
}

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions{
    //显示系统托盘
    [application setStatusBarHidden:NO withAnimation:UIStatusBarAnimationFade];
    
    self.window = [[[UIWindow alloc] initWithFrame:[[UIScreen mainScreen] bounds]] autorelease];
    
    STProductViewController *productViewController = [[STProductViewController alloc]init];
    UINavigationController *productViewControllerNav = [[UINavigationController alloc] initWithRootViewController:productViewController];
    [productViewController release];
    
    STTravelViewController *travelViewController = [[STTravelViewController alloc]init];
    UINavigationController *travelViewControllerNav = [[UINavigationController alloc] initWithRootViewController:travelViewController];
    [travelViewController release];
    
    STMembershipCardViewController *membershipCardViewController = [[STMembershipCardViewController alloc]init];
    UINavigationController *membershipCardViewControllerNav = [[UINavigationController alloc] initWithRootViewController:membershipCardViewController];
    [membershipCardViewController release];
    
    STMemberViewController *memberViewController = [[STMemberViewController alloc]init];
    UINavigationController *memberViewControllerNav = [[UINavigationController alloc] initWithRootViewController:memberViewController];
    [memberViewController release];
    
    STMoreViewController *moreViewController = [[STMoreViewController alloc]init];
    UINavigationController *moreViewControllerNav = [[UINavigationController alloc] initWithRootViewController:moreViewController];
    [moreViewController release];
    
    
    //添加标签控制器
    _tabBarController = [[UITabBarController alloc] init];
    _tabBarController.delegate = self;
    _tabBarController.viewControllers = [NSArray arrayWithObjects:
                                         productViewControllerNav,
                                         travelViewControllerNav,
                                         membershipCardViewControllerNav,
                                         memberViewControllerNav,
                                         moreViewControllerNav,
                                         nil];
    //释放导航对象
    [productViewControllerNav release];
    [travelViewControllerNav release];
    [membershipCardViewControllerNav release];
    [memberViewControllerNav release];
    [moreViewControllerNav release];
    
    self.window.rootViewController=_tabBarController;
    [self.window makeKeyAndVisible];
    return YES;
}

#pragma mark UITab选择事件
- (void)tabBarController:(UITabBarController *)tabBarController didSelectViewController:(UIViewController *)viewController{
    int newTabIndex = tabBarController.selectedIndex;
    if (newTabIndex == m_lastTabIndex) {
        NSLog(@"clickc===%d",m_lastTabIndex);
    }else{
        m_lastTabIndex = newTabIndex;
    }
}



@end

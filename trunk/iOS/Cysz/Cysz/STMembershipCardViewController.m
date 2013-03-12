//
//  STMembershipCardViewController.m
//  Cysz
//
//  Created by Start on 13-3-12.
//  Copyright (c) 2013年 Start. All rights reserved.
//

#import "STMembershipCardViewController.h"

@interface STMembershipCardViewController ()

@end

@implementation STMembershipCardViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        self.tabBarItem.image = [UIImage imageNamed:@"nav_icon_member"];
        self.tabBarItem.title = @"电子门票";
        
        self.navigationItem.title=@"电子门票";
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end

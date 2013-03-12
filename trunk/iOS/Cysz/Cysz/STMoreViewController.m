//
//  STMoreViewController.m
//  Cysz
//
//  Created by Start on 13-3-12.
//  Copyright (c) 2013年 Start. All rights reserved.
//

#import "STMoreViewController.h"

@interface STMoreViewController ()

@end

@implementation STMoreViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        self.tabBarItem.image = [UIImage imageNamed:@"nav_icon_more"];
        self.tabBarItem.title = @"更多";
        
        self.navigationItem.title=@"更多";
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

//
//  ACBaseTableViewController.m
//  ACyulu
//
//  Created by Start on 12-12-8.
//  Copyright (c) 2012年 ancun. All rights reserved.
//

#import "BaseTableViewController.h"

@interface BaseTableViewController ()

@end

@implementation BaseTableViewController

@synthesize tableView=_tableView;

@synthesize dataItemArray=_dataItemArray;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
    }
    return self;
}

- (void) dealloc{
    [_dataItemArray release];
    [_tableView release];
    [super dealloc];
}

- (void) onControllerResult:(NSInteger)resultCode requestCode:(NSInteger)requestCode data:(NSMutableArray *)result{
    
}

#pragma mark -
#pragma mark TableView的处理
- (NSInteger) tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
     return [_dataItemArray count];
}
- (CGFloat) tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    return 62;
}

//生成表视图
- (UITableView*) buildTableView{
    if(_tableView==nil) {
        _tableView=[[UITableView alloc]initWithFrame:
                    CGRectMake(0, 0,
                               self.view.frame.size.width,
                               self.view.frame.size.height)];
        [_tableView setAutoresizingMask:UIViewAutoresizingFlexibleWidth|UIViewAutoresizingFlexibleHeight];
        [_tableView setDelegate:self];
        [_tableView setDataSource:self];
        
        [self.view addSubview:_tableView];
    }
    return _tableView;
}
@end

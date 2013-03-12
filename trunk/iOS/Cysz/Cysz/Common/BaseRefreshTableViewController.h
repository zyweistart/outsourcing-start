//
//  ACBaseRefreshTableViewController.h
//  ACyulu
//
//  Created by Start on 12-12-8.
//  Copyright (c) 2012年 ancun. All rights reserved.
//

#import "BaseTableViewController.h"

@interface BaseRefreshTableViewController : BaseTableViewController<EGORefreshTableHeaderDelegate>
{
    NSInteger _pageSize;
    NSInteger _currentPage;
    
	BOOL _reloading;
    
    BOOL _loadOver;
    
    EGORefreshTableHeaderView *_refreshHeaderView;
}

@property NSInteger pageSize;

@property NSInteger currentPage;

- (void) autoRefresh;

- (void) doneManualRefresh;

- (void) reloadTableViewDataSource;

- (void) doneLoadingTableViewData;

@end

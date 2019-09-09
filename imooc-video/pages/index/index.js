//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    screenWidth: 350,
    totalPage: 1,
    page: 1,
    videoList:[],
    serverUrl:"",
    searchContent:""
   
  },
 
  onLoad: function (params) {
    var that = this;
    var screenWidth = wx.getSystemInfoSync().screenWidth;
    that.setData({
      screenWidth: screenWidth,
    });
    var page = that.data.page;    
    var searchContent = params.search;
    var isSaveRecord = params.isSaveRecord;
    that.setData({
      searchContent: searchContent,
    })
    if(isSaveRecord == null || isSaveRecord == '' || isSaveRecord == undefined){
      isSaveRecord = 0;
    }
    that.getAllVideoList(page,isSaveRecord);
    
  },
  getAllVideoList: function (page, isSaveRecord){
    var that = this;
    var serverUrl = app.serverUrl;

    wx.showLoading({
      title: '加载中...',
    });
    wx.request({
      url: serverUrl + "/video/showAll?page=" + page + "&isSaveRecord=" + isSaveRecord,
      method: "POST",
      data:{
        videoDesc: that.data.searchContent
      },
      success: function (res) {
        wx.hideLoading();
        wx.hideNavigationBarLoading();
        wx.stopPullDownRefresh();
        console.log(res);
        if (page === 1) {
          that.setData({
            videoList: []
          });
        }
        var videoList = res.data.data.rows;
        var newVideoList = that.data.videoList;
        
        that.setData({
          videoList: newVideoList.concat(videoList),
          page: page,
          totalPage: res.data.data.total,
          serverUrl: serverUrl
        })

      }
    })  

  },
  onReachBottom:function(){
    var that = this;
    var currentPage = that.data.page;
    var totalPage = that.data.totalPage;
    if(currentPage === totalPage){
      wx.showToast({
        title: '到底了~~~~',
        icon: 'none',
      })
      return;
    }
    var page = currentPage + 1;
    that.getAllVideoList(page,0);
  },
  onPullDownRefresh:function(){
    wx.showNavigationBarLoading();
    this.getAllVideoList(1,0);
  },
  showVideoInfo: function(e){
    var that = this;
    var videoList = that.data.videoList;
    var arrindex = e.target.dataset.arrindex
    var videoInfo = JSON.stringify(videoList[arrindex])
    wx.redirectTo({
      url: '../videoInfo/videoInfo?videoInfo='+videoInfo,
    })

  }
})

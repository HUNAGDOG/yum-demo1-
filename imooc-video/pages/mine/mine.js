// pages/mine/mine.js
const app = getApp();
Page({

  data: {
     faceUrl:"../resource/images/noneface.png",
     isMe:true,
     isFollow:false,
     videoSelClass:"video-info",
     isSelectedWork:"video-info-selected",
     isSelectedLike:"",
     isSelectedFollow:"",
     myVideoList:[],
     myVideoPage:1,
     myVideoTotal:1,

     likeVideoList:[],
     likeVideoPage: 1,
     likeVideoTotal: 1,

     followVideoList: [],
     followVideoPage: 1,
     followVideoTotal: 1,

     myWorkFlag:false,
     myLikeFlag:true,
     myFollowFlag:true,

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (params) {
    var that = this;
    var userInfo = app.getGlobalUserInfo()
    var userId = userInfo.id;
    var publisherId = params.publisherId;
    
    if (publisherId != null && publisherId != '' && publisherId != undefined && userInfo.id != params.publisherId){
      userId = publisherId;
      that.setData({
        isMe:false,
        publisherId:publisherId
      })

    }
    
    wx.showLoading({
      title: '请等待',
    })
    wx.request({
      url: app.serverUrl + "/user/query?userId=" + userId + "&fanId=" + userInfo.id,
      method:"POST",
      header:{ 'content-type': 'application/json',
      "userId":userInfo.id,
      "uniqueToken":userInfo.uniqueToken
    
       },
      success:function(res){
        wx.hideLoading();
        
        if(res.data.status == 200){
          var userInfo = res.data.data;
          var faceUrl = "../resource/images/noneface.png";
          if (userInfo.faceImage != null && userInfo.faceImage != "" && userInfo.faceImage != undefined){
            faceUrl = app.serverUrl+userInfo.faceImage;
          }
          
          that.setData({
            faceUrl:faceUrl,
            nickname: userInfo.nickname,
            fansCounts: userInfo.fansCounts,
            followCounts: userInfo.followCounts,
            receiveLikeCounts: userInfo.receiveLikeCounts,
            isFollow: userInfo.followed
          })
          that.getMyVideoList(1);
          
        } else if (res.data.status == 502){
          wx.showToast({
            title: res.data.msg,
            icon:"none",
            duration:5000,
            success:function(){
              wx.redirectTo({
                url: '../loginUser/loginUser',
              })
            }
          })
        }
      }
    })

  },
  logout:function(){
    var userInfo = app.getGlobalUserInfo()
    var user = userInfo;
    var serverUrl = app.serverUrl;
    wx.showLoading({
      title: '请等待',
    })
    wx.request({
      url: serverUrl+"/logout?userId="+user.id,
      method:"POST",
      header: { 'content-type': 'application/json' },
      success:function(res){
        wx.hideLoading();
        if(res.data.status == 200){
          wx.showToast({
            title: '注销成功',
            icon:"success",
            duration:3000
          })
          
          wx.removeStorageSync("userInfo");
          wx.redirectTo({
            url: '../loginUser/loginUser',
          })
        }
        console.log(res.data);
      }
    })
  },
  changeFace:function(){
    var userInfo = app.getGlobalUserInfo()
   
    var that = this;
    var serverUrl = app.serverUrl;
    wx.chooseImage({
      count:1,
      sizeType: ['compressed'],
      sourceType: ['album'],
      success: function(res) {
        const tempFilePaths = res.tempFilePaths;  
        console.log(tempFilePaths);
        wx.showLoading({
          title: '上传中',
        })
        console.log(userInfo.id);
       
        wx.uploadFile({
          url: serverUrl + "/user/uploadFace?userId=" + userInfo.id,
          filePath: tempFilePaths[0],
          name: 'file',
          header: {
            'content-type': 'application/json', // 默认值
            'userId': userInfo.id,
            'uniqueToken': userInfo.uniqueToken
          },
          success:function(res){
            wx.hideLoading();
            var data =JSON.parse(res.data);
            console.log(data);
            if(data.status == 200){
              wx.showToast({
                title: '上传成功',
                icon: "success",
                duration: 3000
              }) ;
              var imageUrl = data.data;
              that.setData({
                faceUrl: app.serverUrl+imageUrl
              });
            }else if(data.status == 500){
              wx.showToast({
                title: data.msg,
                icon: "none",
                duration: 3000
              })

            }
          
            


          }
        })

      },
    })

  },
  uploadVideo:function(){
    var taht = this;
    wx.chooseVideo({
      sourceType: ['album', 'camera'],
      success:function(res){
        console.log(res)
        var duration = res.duration;
        var tmpHeight = res.height;
        var tmpWidth = res.width;
        var tmpVideoUrl = res.tempFilePath;
        var tmpCoverUrl = res.thumbTempFilePath;
        
        if (duration > 20){
          wx.showToast({
            title: '视频长度最多十秒',
            icon:"none",
            duration:2500
          })
        } else if (duration < 1){
          wx.showToast({
            title: '视频太短了',
            icon: "none",
            duration: 2500
          })
        }else{
          wx.redirectTo({
            url: '../chooseBgm/chooseBgm?duration=' + duration 
              + "&tmpHeight=" + tmpHeight
              + "&tmpWidth=" + tmpWidth 
              + "&tmpVideoUrl=" + tmpVideoUrl 
              + "&tmpCoverUrl=" + tmpCoverUrl,
          })
        }

      }
    })
  },
  followMe:function(e){
    var that = this;
    var publisherId = that.data.publisherId;
    var userInfo = app.getGlobalUserInfo();
    var  userId = userInfo.id;
    var followType = e.currentTarget.dataset.followtype;
    var url = "";
    if(followType == "1"){
      url = "/user/beyourfans?userId=" + publisherId + "&fanId=" + userId;
    }else{
      url = "/user/nobeyourfans?userId=" + publisherId + "&fanId=" + userId;
    }
    wx.showLoading();
    wx.request({
      url: app.serverUrl + url,
      method:"POST",
      header: { 'content-type': 'application/json',
        "userId": userInfo.id,
        "uniqueToken": userInfo.uniqueToken

      },
      success:function(res){
        wx.hideLoading();
        if (followType == "1") {
          that.setData({
            isFollow:true,
            fansCounts:++that.data.fansCounts
          })
        } else {
          that.setData({
            isFollow: false,
            fansCounts: --that.data.fansCounts
          })
        }


      }
    })
  },
  doSelectWork:function(){
    var that = this;
    that.setData({
      isSelectedWork: "video-info-selected",
      isSelectedLike: "",
      isSelectedFollow: "",

      myVideoList: [],
      myVideoPage: 1,
      myVideoTotal: 1,

      likeVideoList: [],
      likeVideoPage: 1,
      likeVideoTotal: 1,

      followVideoList: [],
      followVideoPage: 1,
      followVideoTotal: 1,

      myWorkFlag: false,
      myLikeFlag: true,
      myFollowFlag: true,
    })
    this.getMyVideoList(1);
  },
  doSelectLike:function(){
    var that = this;
    
    that.setData({
      isSelectedWork: "",
      isSelectedLike: "video-info-selected",
      isSelectedFollow: "",
      myVideoList: [],
      myVideoPage: 1,
      myVideoTotal: 1,

      likeVideoList: [],
      likeVideoPage: 1,
      likeVideoTotal: 1,

      followVideoList: [],
      followVideoPage: 1,
      followVideoTotal: 1,

      myWorkFlag: true,
      myLikeFlag: false,
      myFollowFlag: true,

    })
    
    
    this.getMyLikesList(1);
  },
  doSelectFollow:function(){
    var that = this;
    that.setData({
      isSelectedWork: "",
      isSelectedLike: "",
      isSelectedFollow: "video-info-selected",

      myVideoList: [],
      myVideoPage: 1,
      myVideoTotal: 1,

      likeVideoList: [],
      likeVideoPage: 1,
      likeVideoTotal: 1,

      followVideoList: [],
      followVideoPage: 1,
      followVideoTotal: 1,

      myWorkFlag: true,
      myLikeFlag: true ,
      myFollowFlag: false,

    })
    this.getMyFollowList(1)
  },
  getMyVideoList: function (page) {
    var me = this;
    var userInfo = app.getGlobalUserInfo();
    var userId = me.data.publisherId;
    if(userId == null || userId == '' || userId == undefined){
      userId=userInfo.id;
    }
    // 查询视频信息
    wx.showLoading();
    // 调用后端
    var serverUrl = app.serverUrl;
    wx.request({
      url: serverUrl + '/video/showAll/?page=' + page + '&pageSize=6',
      method: "POST",
      data: {
        userId: userId
      },
      header: {
        'content-type': 'application/json' // 默认值
      },
      success: function (res) {
        console.log(res.data);
        var myVideoList = res.data.data.rows;
        wx.hideLoading();

        var newVideoList = me.data.myVideoList;
        me.setData({
          myVideoPage: page,
          myVideoList: newVideoList.concat(myVideoList),
          myVideoTotal: res.data.data.total,
          serverUrl: app.serverUrl
        });
      }
    })
  },
  getMyLikesList: function (page) {
    var me = this;
    var userInfo = app.getGlobalUserInfo();
    var userId = me.data.publisherId;
    if (userId == null || userId == '' || userId == undefined) {
      userId = userInfo.id;
    }
    // 查询视频信息
    wx.showLoading();
    // 调用后端
    var serverUrl = app.serverUrl;
    wx.request({
      url: serverUrl + '/video/showMyLike/?userId=' + userId + '&page=' + page + '&pageSize=6',
      method: "POST",
      header: {
        'content-type': 'application/json' // 默认值
      },
      success: function (res) {
        console.log(res.data);
        var likeVideoList = res.data.data.rows;
        wx.hideLoading();

        var newVideoList = me.data.likeVideoList;
        me.setData({
          likeVideoPage: page,
          likeVideoList: newVideoList.concat(likeVideoList),
          likeVideoTotal: res.data.data.total,
          serverUrl: app.serverUrl
        });
      }
    })
  },
  getMyFollowList: function (page) {
    var me = this;
    var userInfo = app.getGlobalUserInfo();
    var userId = me.data.publisherId;
    if (userId == null || userId == '' || userId == undefined) {
      userId = userInfo.id;
    }

    // 查询视频信息
    wx.showLoading();
    // 调用后端
    var serverUrl = app.serverUrl;
    wx.request({
      url: serverUrl + '/video/showMyFollow/?userId=' + userId + '&page=' + page ,
      method: "POST",
      header: {
        'content-type': 'application/json' // 默认值
      },
      success: function (res) {
        console.log(res.data);
        var followVideoList = res.data.data.rows;
        wx.hideLoading();

        var newVideoList = me.data.followVideoList;
        me.setData({
          followVideoPage: page,
          followVideoList: newVideoList.concat(followVideoList),
          followVideoTotal: res.data.data.total,
          serverUrl: app.serverUrl
        });
      }
    })
  },
  // 到底部后触发加载
  onReachBottom: function () {
    var myWorkFalg = this.data.myWorkFalg;
    var myLikesFalg = this.data.myLikesFalg;
    var myFollowFalg = this.data.myFollowFalg;

    if (!myWorkFalg) {
      var currentPage = this.data.myVideoPage;
      var totalPage = this.data.myVideoTotal;
      // 获取总页数进行判断，如果当前页数和总页数相等，则不分页
      if (currentPage === totalPage) {
        wx.showToast({
          title: '已经没有视频啦...',
          icon: "none"
        });
        return;
      }
      var page = currentPage + 1;
      this.getMyVideoList(page);
    } else if (!myLikesFalg) {
      var currentPage = this.data.likeVideoPage;
      var totalPage = this.data.myLikesTotal;
      // 获取总页数进行判断，如果当前页数和总页数相等，则不分页
      if (currentPage === totalPage) {
        wx.showToast({
          title: '已经没有视频啦...',
          icon: "none"
        });
        return;
      }
      var page = currentPage + 1;
      this.getMyLikesList(page);
    } else if (!myFollowFalg) {
      var currentPage = this.data.followVideoPage;
      var totalPage = this.data.followVideoTotal;
      // 获取总页数进行判断，如果当前页数和总页数相等，则不分页
      if (currentPage === totalPage) {
        wx.showToast({
          title: '已经没有视频啦...',
          icon: "none"
        });
        return;
      }
      var page = currentPage + 1;
      this.getMyFollowList(page);
    }

  },
  // 点击跳转到视频详情页面
  showVideo: function (e) {
    var that = this;

    console.log(e);
  
    var myWorkFalg = that.data.myWorkFlag;
    var myLikesFalg = that.data.myLikeFlag;
    var myFollowFalg = that.data.myFollowFlag;
    

    if (!myWorkFalg) {
      var videoList = that.data.myVideoList;      
    } else if (!myLikesFalg) {
      var videoList = that.data.likeVideoList;
    } else if (!myFollowFalg) {
      var videoList = that.data.followVideoList;
    }   
    
    var arrindex = e.target.dataset.arrindex
    var videoInfo = JSON.stringify(videoList[arrindex])
    wx.redirectTo({
      url: '../videoInfo/videoInfo?videoInfo=' + videoInfo,
    })

  },

})
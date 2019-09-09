  // pages/videoInfo/videoInfo.js
var videoUtil = require('../../utils/videoUtils.js')
const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    cover: "cover",
    videoId: "",
    src: "",
    videoInfo: {},
    userLikeVideo:false,
    placeholder:"说点什么.......",
    contentValue:"",
    commentsPage:1,
    commentsTotal:1,
    commentsList:[]
  },
  videoCtx: {},
  onLoad: function(params) {
    var that = this;
    that.videoCtx = wx.createVideoContext("myVideo", that);
    var videoInfo = JSON.parse(params.videoInfo);
    var height = videoInfo.videoHeight;
    var width = videoInfo.videoWidth;
    var cover = "cover";
    if (height < width) {
      cover = "";
    }
    that.setData({
      videoid: videoInfo.id,
      src: app.serverUrl + videoInfo.videoPath,
      videoInfo: videoInfo,
      cover: cover
    })
    var userInfo = app.getGlobalUserInfo();
    var loginUserId = "";
    if (userInfo != null && userInfo != undefined && userInfo != ""){
      loginUserId = userInfo.id;
          }
    wx.request({
      url: app.serverUrl + "/user/querypublisher?loginUserId=" + loginUserId + "&videoId=" + videoInfo.id +"&publisherUserId=" + videoInfo.userId,
      method:"POST",
      success:function(res){
        console.log(res);
        var publisher = res.data.data.usersVO
        var userLikeVideo = res.data.data.userLikeVideo;
        that.setData({
          publisher: publisher,
          userLikeVideo: userLikeVideo,
          serverUrl:app.serverUrl

        })

      }
    })
    that.videoCtx.play();
    that.getCommentsList(1);


  },
  onShow: function() {
    var that = this;
   // that.videoCtx.play();

  },
  onHide: function() {
    var that = this;
    that.videoCtx.pause();

  },
  showSearch: function() {
    wx.redirectTo({
   
      url: '../searchVideo/searchVideo',
    })
  },
  showPublisher:function(){
    var that = this;
    var userInfo = app.getGlobalUserInfo();
    var videoInfo = that.data.videoInfo;
    var realUrl = "../mine/mine#publisherId@" + videoInfo.userId;
    if (userInfo == null || userInfo == undefined || userInfo == "") {
      wx.redirectTo({
        url: '../loginUser/loginUser?redirectUrl=' + realUrl,
      })
    } else {
     
      wx.redirectTo({
        url: '../mine/mine?publisherId=' + videoInfo.userId,
      })

    }


  },
  upload: function() {
    var that = this;
    var userInfo = app.getGlobalUserInfo();
    var videoInfo = JSON.stringify(that.data.videoInfo)
    var realUrl = "../videoInfo/videoInfo#videoInfo@" + videoInfo;

    if (userInfo == null || userInfo == undefined || userInfo == "") {
      wx.redirectTo({
        url: '../loginUser/loginUser?redirectUrl=' + realUrl,
      })
    } else {
      videoUtil.uploadVideo();

    }
   

  },
  showIndex: function() {
    wx.redirectTo({
      url: '../index/index',
    })
  },
  showMine: function() {
    var userInfo = app.getGlobalUserInfo();
        
      wx.navigateTo({
        url: '../mine/mine',
      })
    
  },
  likeVideoOrNot:function(){
    var that = this;
    var userInfo = app.getGlobalUserInfo();
    var videoInfo = that.data.videoInfo;
    if(userInfo == null || userInfo == '' || userInfo == undefined){
      wx.redirectTo({
        url: '../loginUser/loginUser',
      })
    }else{
      var userLikeVideo = that.data.userLikeVideo;
      var url = app.serverUrl+"/video/userLike?userId=" + userInfo.id + "&videoId=" + videoInfo.id + "&videoCreaterId=" + videoInfo.userId;
      if (userLikeVideo){
        url = app.serverUrl + "/video/userUnLike?userId=" + userInfo.id + "&videoId=" + videoInfo.id + "&videoCreaterId=" + videoInfo.userId;
      }
      wx.showLoading({
        title: '...',        
      })
      wx.request({
        url: url,
        method:"POST",
        header: {
          'content-type': 'application/json',
          "userId": userInfo.id,
          "uniqueToken": userInfo.uniqueToken
        },
        success:function(res){
          wx.hideLoading();
          that.setData({
            userLikeVideo: !userLikeVideo
          })


        }
      })


    }

  },
  shareMe:function(){
    var that = this;
    var userInfo = app.getGlobalUserInfo();
    wx.showActionSheet({
      itemList: ['下载到本地','举报用户','分享到朋友圈','分享到QQ空间','分享到微博'],
      success:function(res){
        console.log(res.tapIndex);
        if(res.tapIndex === 0){
          wx.showLoading()
         wx.downloadFile({
           url:app.serverUrl + that.data.videoInfo.videoPath,
           success:function(res){
             if(res.statusCode == 200){
               console.log(res.tempFilePath);
               wx.saveVideoToPhotosAlbum({
                 filePath: res.tempFilePath,
                 success:function(res){
                   wx.hideLoading();
                   console.log(res.errMsg);

                 }
               })
             }

           }

         })
        } else if (res.tapIndex === 1){
          var videoInfo = JSON.stringify(that.data.videoInfo);
          var realUrl = '../videoinfo/videoinfo#videoInfo@' + videoInfo;

          if (userInfo == null || userInfo == undefined || userInfo == '') {
            wx.navigateTo({
              url: '../userLogin/login?redirectUrl=' + realUrl,
            })
          } else {
            var publishUserId = that.data.videoInfo.userId;
            var videoId = that.data.videoInfo.id;
            
            wx.navigateTo({
              url: '../report/report?videoId=' + videoId + "&publishUserId="
                + publishUserId 
            })
          }

        }else{
          wx.showToast({
            title: 'API未开放..',
            icon: "none",
            duration: 3000
          })
        }
      }
    })
  },
  onShareAppMessage:function(res){
    var that = this;
    var videoInfo = JSON.stringify(that.data.videoInfo);
   return{
      title:"自定义转发标题",
      path:"pages/videoInfo/videoInfo?videoInfo="+videoInfo
    }

  },
  leaveComment:function(){
    this.setData({
      commentFocus:true
    })
  },
  saveComment:function(e){
    var that = this;
    var comment = e.detail.value;
    var fatherCommentId = e.currentTarget.dataset.replyfathercommentid;
    var toUserId = e.currentTarget.dataset.replytouserid;
    var userInfo = app.getGlobalUserInfo();
    var videoInfo = JSON.stringify(that.data.videoInfo)
    var realUrl = "../videoInfo/videoInfo#videoInfo@" + videoInfo;
    

    if (userInfo == null || userInfo == undefined || userInfo == "") {
      wx.redirectTo({
        url: '../loginUser/loginUser?redirectUrl=' + realUrl,
      })
    }else{
      wx.request({
        url: app.serverUrl + '/video/saveComment?fatherCommentId=' + fatherCommentId + "&toUserId=" + toUserId,
        method:"POST",
        header:{
          'contentType':'application/json',
          'userId':userInfo.id,
          'uniqueToken':userInfo.uniqueToken
        },
        data:{
          fromUserId: userInfo.id,
          videoId: that.data.videoInfo.id,
          comment: comment
        },
        success:function(res){
          that.setData({
            contentValue:"",
            commentsList:[]
          })
          
        }
      })
    }
    that.getCommentsList(1);
  },
  getCommentsList:function(page){
    var that = this;
    var videoId = that.data.videoInfo.id;
    
    //commentsPage: 1,
    //  commentsTotal: 1,
     //   commentsList: []
     
    wx.request({
      url: app.serverUrl + "/video/getVideoComment?videoId=" + videoId + "&page=" + page
      + "&pageSize=6",
      method:"POST",
      success:function(res){
        
        var commentsList = res.data.data.rows;
        var newCommentsList = that.data.commentsList
        
        that.setData({
          commentsList: newCommentsList.concat(commentsList),
          commentsPage:page,
          commentsTotal:res.data.data.total
        })
      }
    })

  },
  onReachBottom: function () {
    var me = this;
    var currentPage = me.data.commentsPage;
    var totalPage = me.data.commentsTotal;
    if (currentPage === totalPage) {
      return;
    }
    var page = currentPage + 1;
    me.getCommentsList(page);
  },
  replyFocus:function(e){
    var fatherCommentId = e.currentTarget.dataset.fathercommentid;
    var toUserId = e.currentTarget.dataset.touserid;
    var toNickname = e.currentTarget.dataset.tonickname;
    var that = this;
     that.setData({
       placeholder:"回复" + toNickname,
       replyfatherCommentId: fatherCommentId,
       replyToUserId: toUserId,
       commentFocus:true,
       contentValue:"",
       commentsList:[]

    })
  }


})
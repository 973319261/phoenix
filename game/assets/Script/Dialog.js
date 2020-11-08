/**
 * 弹窗脚本
 */
cc.Class({
    extends: cc.Component,

    properties: {
        dragingItem:null,//当前拖拽对象（回收）
        goldLabel:{
            default:null,
            type:cc.Label,
            tooltip: "商店总金币label",
        },
        shopItemPrefab:{
            default:null,
            type:cc.Prefab,
            tooltip: "商店item预制体",
        },//商店item
        rankingItemPrefab:{
            default:null,
            type:cc.Prefab,
            tooltip: "排行榜item预制体",
        },//排行榜item
        shopScrollView:{
            default:null,
            type:cc.ScrollView,
            tooltip: "商店滚动容器",
        },//滚动容器
        rankingScrollView:{
            default:null,
            type:cc.ScrollView,
            tooltip: "排行榜滚动容器",
        },//滚动容器
        
    },

    // LIFE-CYCLE CALLBACKS:

    onLoad () {
        if(this.goldLabel!=null){//商店总金币label不为空
            this.goldLabel.string=MergeManage.currencyUnit(userInfo.goldUsable);//可用金币
        }
        if(this.shopScrollView !=null){//商店滚动容器
            this.shopContent = this.shopScrollView.content;  
            this.initShopData();//初始化商店数据
        }
        if(this.rankingScrollView !=null){//排行榜滚动容器
            this.rankingContent = this.rankingScrollView.content;  
            this.initRankingData();//初始化排行榜数据
        }
    },

    start () {
    },
    update (dt) {
        this.goldLabel.string=MergeManage.currencyUnit(userInfo.goldUsable);
    },
    /**
     * 初始化商店数据
     */
    initShopData(){
        //商店数据绑定
        if(this.shopItemPrefab!=null && this.shopScrollView!=null){//商品宠物列表预制体和滚动容器不为空
            for(let i=0;i<petInfo.length;++i){
                let shopItem=this.addShopItem();
                shopItem.getComponent('ShopItem').init(petInfo[i]);//初始化item数据
            }
        }
    },
    /**
     * 初始化排行榜数据
     */
    initRankingData(){
        //排行榜数据绑定
        if(this.rankingItemPrefab!=null && this.rankingScrollView!=null){//商品宠物列表预制体和滚动容器不为空
            for(let i=0;i<rankingInfo.length;i++){
                let rankingNum=i;//排行号
                let rankingItem=this.addRankingItem();
                rankingItem.getComponent('RankingItem').init(rankingInfo[i],rankingNum+1);//初始化item数据
            }
        }
    },
    /**
     * 创建商店item
     */
    addShopItem(){
        let item = cc.instantiate(this.shopItemPrefab);
        this.shopContent.addChild(item);
        return item;
    },
    /**
     * 创建排行榜item
     */ 
    addRankingItem(){
        let item = cc.instantiate(this.rankingItemPrefab);
        this.rankingContent.addChild(item);//添加item
        return item;
    },
    /**
    * 显示弹窗
    * @param {*} dragingItem 外部传入item值
    */
    show(dragingItem){
        if(dragingItem){
            this.dragingItem=dragingItem;//设置item
            this.dragingItem.setItem(this.dragingItem.item);//item返回原来的位置
        }
        if(this.shopScrollView!=null){
            this.initShopData();//初始化商店数据
        }
        var anim = this.node.getComponent(cc.Animation);//获取动画
        anim.play();//播放动画
        this.node.active=true;//显示窗口
    },
    /**
     * 关闭弹窗
     * @param {*} dragingItem 外部传入item值
     */
    hide(){
        if(this.shopScrollView !=null){
            this.shopContent.removeAllChildren();//移除商店所有列表item
        }
        this.node.active=false;//隐藏节点
        if(this.dragingItem){
            this.dragingItem.item.node.active=true;//显示item节点
            this.dragingItem.setItem(this.dragingItem.item);//item返回原来的位置
        }
    },
     /**
     * 邀请好友事件
     */
    onInviteClick(){
        if(this.node){
            console.log('微信邀请接口');
           //微信邀请接口
        }
    },
    //============加速弹窗开始=============//
     /**
     * 观看视频事件
     */
    onWatchClick(){
        if(this.node){
            console.log('观看视频');
           //微信邀请接口
        }
    },
    //============加速弹窗结束=============//
    //============回收弹窗开始=============//
    /**
     * 确定
     */
    onConfirmClick(){
        if(this.dragingItem){
            MergeManage.doneDragRecycle(this.dragingItem);//回收item
            this.node.active=false;//关闭窗口
            this.dragingItem=null;//清空对象
        }
    },
    //============回收弹窗结束=============//
});

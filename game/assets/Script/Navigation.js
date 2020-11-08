/**
 * 导航栏脚本
 */
cc.Class({
    extends: cc.Component,

    properties: {
        rankingPrefab:{
            default:null,
            type:cc.Prefab,
            tooltip: "排行榜弹窗",
        },
        handbookPrefab:{
            default:null,
            type:cc.Prefab,
            tooltip: "图鉴弹窗",
        },
        dialPrefab:{
            default:null,
            type:cc.Prefab,
            tooltip: "转盘弹窗",
        },
        explainPrefab:{
            default:null,
            type:cc.Prefab,
            tooltip: "说明弹窗",
        },
        speedPrefab:{
            default:null,
            type:cc.Prefab,
            tooltip: "加速弹窗",
        },
        invitePrefab:{
            default:null,
            type:cc.Prefab,
            tooltip: "邀请弹窗",
        },
        shopPrefab:{
            default:null,
            type:cc.Prefab,
            tooltip: "商店弹窗",
        },
        hintPrefab:{
            default:null,
            type:cc.Prefab,
            tooltip: "提示弹窗",
        },
        petImg:{
            default:null,
            type:cc.Sprite,
            tooltip:'可创建宠物头像'
        },
        gradeLabel:{
            default:null,
            type:cc.Label,
            tooltip: "可创建宠物等级",
        },  
        goldLabel:{
            default:null,
            type:cc.Label,
            tooltip: "创建宠物所需金币",
        },
    },


    onLoad () {
        window.Navigation=this;
        this.rankingDialog=cc.instantiate(this.rankingPrefab);//克隆排行榜弹窗节点
        this.handbookDialog=cc.instantiate(this.handbookPrefab);//克隆图鉴弹窗节点  
        this.dialDialog=cc.instantiate(this.dialPrefab);//克隆转盘弹窗节点
        this.explainDialog=cc.instantiate(this.explainPrefab);//克隆说明弹窗节点
        this.speedDialog=cc.instantiate(this.speedPrefab);//克隆加速弹窗节点
        this.inviteDialog=cc.instantiate(this.invitePrefab);//克隆邀请弹窗节点
        this.shopDialog=cc.instantiate(this.shopPrefab);//克隆商店弹窗节点
        this.hintDialog=cc.instantiate(this.hintPrefab);//克隆提示弹窗
    },

    start () {

    },

    // update (dt) {},
     /**
     * 排行榜
     */
    /**
     * 初始化数据
     */
    init(){
        if(this.gradeLabel!=null && this.petImg!=null && this.goldLabel!=null){
            for(let i=0;i<petInfo.length;i++){//获取可创建的最大等级
                if(petInfo[i].isCreate==1){
                   window.maxGrade=petInfo[i].petLevel;//保存可创建的最大等级到全局变量
                }
            }
            this.setCreateData(maxGrade);
        }
    },
    /**
     * 设置创建按钮参数
     */
    setCreateData(maxGrade){
        this.gradeLabel.string='Lv.' + maxGrade;//设置等级
        var pet = MergeManage.findPetInfoByLevel(maxGrade);//当前item等级宠物信息
        let gold=pet.petGoldLevel==0?pet.petGold:pet.petGoldLevel * pet.petGold;//计算金币
        this.goldLabel.string=gold;//设置金币到创建按钮金币label
        //设置头像
        cc.loader.loadRes("image/sprite_atlas_create", cc.SpriteAtlas, function (err, atlas) {
            var frame = atlas.getSpriteFrame('create_'+ maxGrade);
            this.petImg.spriteFrame = frame;
        }.bind(this));
    },
    onRankingClick(){
        if(this.rankingDialog){
            this.rankingDialog.parent = topParent;//设置弹窗到根节点中
            this.rankingDialog.getComponent('Dialog').show();//显示弹窗
        }
    },
     /**
     * 图鉴
     */
    onHandbookClick(){
        if(this.handbookDialog){
            this.handbookDialog.parent = topParent;//设置弹窗到根节点中
            this.handbookDialog.getComponent('Dialog').show();//显示弹窗
        }
    },
     /**
     * 转盘
     */
    onDialClick(){
        if(this.dialDialog){
            this.dialDialog.parent = topParent;//设置弹窗到根节点中
            this.dialDialog.getComponent('Dialog').show();//显示弹窗
        }
    },
     /**
     * 说明
     */
    onExplainClick(){
        if(this.explainDialog){
            this.explainDialog.parent = topParent;//设置弹窗到根节点中
            this.explainDialog.getComponent('Dialog').show();//显示弹窗
        }
    },
     /**
     * 活动
     */
    onActivityClick(){
        // MyJSInterface.toActivity("EventActivity");//调用JAVA方法跳转到活动消息页面
     
    },
     /**
     * 加速
     */
    onSpeedClick(){
        if(this.speedDialog){
            this.speedDialog.parent = topParent;//设置弹窗到根节点中
            this.speedDialog.getComponent('Dialog').show();//显示弹窗
        }
    },
    /**
     * 邀请劵
     */
    onInviteClick(){
        if(this.inviteDialog){
            this.inviteDialog.parent = topParent;//设置弹窗到根节点中
            this.inviteDialog.getComponent('Dialog').show();//显示弹窗
        }
    },
    /**
     * 创建
     */
    onCreateClick(){
         MergeManage.addItemByGradeNum(maxGrade);
    },
    /**
     * 商店
     */
    onShopClick(){
        if(this.shopDialog){
            this.shopDialog.parent = topParent;//设置弹窗到根节点中
            this.shopDialog.getComponent('Dialog').show();//显示弹窗
        }
    },
    /**
     * 显示提示
     */
    showHint(str){
        if(this.hintDialog!=null){
            this.hintDialog.parent = topParent;//设置弹窗到根节点中
            this.hintDialog.getComponent('HintDialog').show(str);//显示弹窗
        }
    },
});

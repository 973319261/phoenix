//合并管理器脚本
var MyObjectPool=require("MyObjectPool");
var ItemBase=require("ItemBase");
cc.Class({
    extends: cc.Component,
    properties: {
        templateWidth:{
            default:0,
            displayName: "TemplateWidth",
            tooltip: "模板的宽度",
        },
        templateHeight:{
            default:0,
            displayName:"TemplateHeight",
            tooltip:"模板的高度",
        },
        rowCount:{
            default:0,
            displayName:"RowCount",
            tooltip:"行数"
        },
        colCount:{
            default:0,
            displayName:"ColumnCount",
            tooltip:"列数"
        },
        totalCount:{
            default:0,
            visible:false,
            displayName:"TotalCount",
            tooltip:"单个物体的总个数"
        },
       itemBase:{
           default:null,
           type:cc.Prefab,
           displayName:"ItemBase",
           tooltip:"单个物体底座背景的预制体"
       },
       item:{
           default:null,
           type:cc.Prefab,
           displayName:"Item",
           tooltip:"可拖拽进行合并的预制体"
       },
       //item底座数组
       itemBaseArray:{
           visible:false,
           default:[],
           type:[ItemBase]
       },
       //item对象池
        itemPool:null,
        //需要使用的等级数据
        useData:null,
        //item物体的数组 暴露在外面可供外面访问
        itemArray:[],
        tempNode:null,//拖拽对象时显示的灰色item
        recyclePrefab:cc.Prefab,//回收弹窗
        maxGoldGrade:60,//最大金币等级
    },


    onLoad () {
        window.MergeManage=this;//设置全局变量访问
        this.initItemPool();//初始化对象池
        this.useData=this.getData();//初始化使用的数据
        this.node.on(cc.Node.EventType.TOUCH_START,this.onTouchStart,this);//绑定触摸开始事件
        this.node.on(cc.Node.EventType.TOUCH_MOVE,this.onTouchMove,this);//绑定拖动事件
        this.node.on(cc.Node.EventType.TOUCH_END,this.onTouchEnd,this);//绑定触摸结束事件
        this.node.on(cc.Node.EventType.TOUCH_CANCEL,this.onTouchCancel,this);//绑定触摸取消事件
        this.recycleBox=cc.find("Canvas/bg/bottomBar/recycle");//查找回收箱
        this.recycleDialog=cc.instantiate(this.recyclePrefab);//克隆弹窗节点
    },

    start () {
        this.createItemBaseMatrix();//初始化底座
    },

    update (dt) {},
     /**
     * 初始化数据
     */
    init(){
        var petList = userInfo.petList;
        var strPetlist=petList.split(',');
        //获取item数据
        for(var i=0;i<strPetlist.length;i++){
            this.itemArray.push(strPetlist[i]);
        }
        //初始化item
        for(let i=0;i<this.itemBaseArray.length;i++){
            let itemGradeNum=(this.itemArray[i]===undefined)? 0 : this.itemArray[i];
            this.itemBaseArray[i].setItemByGradeNum(itemGradeNum);
        }
        this.playGoldToDuration(10);//每隔10秒播放金币动画
    },
    /**
     * 触摸开始
     * @param {*} event 
     * this,dragingItem 拖拽对象
     */
    onTouchStart(event){
        let pos=event.getLocation();//获取触摸位置
        if(this.dragingItem == null){
            this.dragingItem=this.getItemBaseByPosition(pos);//根据世界坐标获取item
            
        }else{//防止同时按下两个宠物背景不会消失
            return ;
        }
        if(this.dragingItem && this.dragingItem.item){
            //拖拽对象拖拽时显示的背景
            var position=this.dragingItem.item.node.position;//获取item原来所在itemBase的相对位置
            this.tempNode = cc.instantiate(this.dragingItem.item.node);//克隆item节点
            this.tempNode.parent = this.dragingItem.node;//设置父容器itemBase
            this.tempNode.color=cc.Color.GRAY;//设置节点颜色（改变精灵颜色）
            this.tempNode.position=position;//设置位置
             
            this.dragingItem.item.node.position=pos;
            this.dragingItem.item.node.parent=this.node;//设置父节点
            this.dragingItem.item.node.setSiblingIndex(this.totalCount+1);
            this.dragingItem.item.node.position=this.node.convertToNodeSpaceAR(pos);//设置触摸拖拽对象的位置
            this.dragingItem.item.setDragState(true);
        }
    },
    /**
     * 开始拖动
     * @param {*} event 
     */
    onTouchMove(event){
        if (this.dragingItem == null || this.dragingItem.item == null) {
            return;
        }
        this.dragingItem.item.node.position = this.node.convertToNodeSpaceAR(event.getLocation());//拖拽对象随触摸位置移动
    },
    /**
     * 触摸结束（触摸当前容器内）
     * @param {*} event 
     * itemBase 目标对象
     * this.dragingItem 拖拽对象
     */
    onTouchEnd(event){
        if(this.dragingItem==null || this.dragingItem.item==null){
            this.dragingItem=null;
            return;
        }
        let world_pos=event.getLocation();//获取世界坐标
        let itemBase=this.getItemBaseByPosition(world_pos);//需要拖拽到的目标位置
        if(itemBase==null || itemBase==this.dragingItem){
            this.dragingItem.setItem(this.dragingItem.item,null,false,false);//回到原位置
        }else{
            //合成Item进行升级
            if(itemBase.item !=null && (this.dragingItem.item.itemData.gradeNum===itemBase.item.itemData.gradeNum)
             && itemBase.item.itemData.gradeNum<this.useData.length){//等级相同 item升级
                this.dragingItem.removeItem();//移除拖拽对象
                itemBase.updateItem();//升级目标对象
                itemBase.setItem(itemBase.item,itemBase.item.itemData,false,false);//把目标对象重新设置到目标对象位置（更新信息）
                this.dragingItem.cleanGrageTag();//清空拖拽对象的等级标签
                this.dragingItem.setItem(null);
                var level=itemBase.item.itemData.gradeNum;//等级数
                if(level>userInfo.level){//如果大于数据库等级就改变
                    //修改用户等级信息
                    HttpHelper.request({
                        url: 'updateGameInfo',
                        method: 'POST',
                        type: 'json',
                        data: {userId:userId,level:level,levelImg:level},
                        success: function(res) {
                            userInfo.level=level;
                            Header.init(level)
                        }.bind(this),
                        erro: function(err) {
                            console.log(err)
                        } 
                    });
                }
                if(level-maxGrade == 5){//如果当前等级与可创建等级相差5
                    //修改宠物列表等级
                    HttpHelper.request({
                        url: 'updatePetLevel',
                        method: 'POST',
                        type: 'json',
                        data: {userId:userId,petInfoId:level},
                        success: function(res) {
                            maxGrade=level-4;//修改可创建最大等级
                            Navigation.setCreateData(maxGrade);
                            petInfo[level-1].isLocked = 1;//当前等级解锁
                            petInfo[level-5].isCreate = 1;//修改当前等级-4的创建状态
                        }.bind(this),
                        erro: function(err) {
                            console.log(err)
                        } 
                    });
                }
            }else{//item交换、移动
                let temp = itemBase.item;
                itemBase.setItem(this.dragingItem.item,this.dragingItem.item.itemData,false,false);//把拖拽对象设置到目标对象位置中
                if(temp!=null){//目标item不为null时  //item交换
                    this.dragingItem.setItem(temp,temp.itemData,false,false);
                }else{//item移动
                    this.dragingItem.cleanGrageTag();//清空拖拽对象的等级标签
                    this.dragingItem.setItem(null);
                }
            }
        }
         // 完成一次拖动
         this.dragingItem = null;//释放拖拽对象资源
         this.tempNode.destroy();//销毁拖拽对象灰色背景
        
    },
    /**
     *  触摸取消(触摸当前容器外)
     */
    onTouchCancel(event){
        if (this.dragingItem == null || this.dragingItem.item == null) {
            this.dragingItem = null;
            return;
        }
        let world_pos=event.getLocation();//获取世界坐标
        //判断是否拖到了回收站
        if(this.isDragToRecycle(world_pos)){
            if(this.recycleDialog){
                this.recycleDialog.parent = topParent;//设置弹窗到根节点中
                this.recycleDialog.getComponent('Dialog').show(this.dragingItem);//设置dragingItem到RecycleDialog组件中
            }
        }else{//回到原位置
            this.dragingItem.setItem(this.dragingItem.item,false,false);
        }
       // 完成一次拖动
       this.dragingItem = null;//释放拖拽对象资源
       this.tempNode.destroy();//销毁拖拽对象灰色背景
    },
    /**
     * 根据世界坐标获取item
     * @param {*} v2 
     */
    getItemBaseByPosition(v2){
        return this.itemBaseArray.find(t => {
            let bounding = t.node.getBoundingBoxToWorld();
            if (bounding.x <= v2.x && bounding.x + bounding.width >= v2.x &&
                bounding.y <= v2.y && bounding.y + bounding.height >= v2.y) {
                return true;
            }
            return false;
        });
    },
    /**
     * 创建item背景底预制体座矩阵
     * 排列方式左上到右下,增加方式从左到右  y轴减小   x 轴增加
     *  1  2  3  4
     *  5  6  7  8
     */
    createItemBaseMatrix(){
        if(this.itemBase==null){//判断预制体是否为空，为空直接退出当前方法
            console.error("ItemBast预制体为空");
            return;
        }
         //预制体的尺寸
        let itemBaseWidth=this.itemBase.data.getContentSize().width;//预制体的宽度
        let itemBaseHeight=this.itemBase.data.getContentSize().height; //预制体的高度
        if(this.templateWidth<itemBaseWidth*this.colCount || this.templateHeight<itemBaseHeight*this.rowCount){//判断设置行列的总尺寸是否超过了模板尺寸
            console.error("设置行列的总尺寸超过了模板尺寸");
            return;
        }
        //底座之间的间隔
        let colInterval=(this.templateWidth-itemBaseWidth*this.colCount)/(this.colCount+1);//列距
        let rowInterval=(this.templateHeight-itemBaseHeight*this.rowCount)/(this.rowCount+1);//行距
        let leftTopPos=cc.v2(-this.templateWidth*0.5,this.templateHeight*0.5);//左上点坐标位置上添加  锚点为0.5  0.5 
        let nameNumber=0;
        //遍历添加item底座背景
        for(let i=0;i<this.rowCount;i++){
            for(let j=0;j<this.colCount;j++){
                let _itemBase=cc.instantiate(this.itemBase);
                _itemBase.parent=this.node;//添加到当前节点中
                let pos=cc.v2(leftTopPos.x+colInterval*(j+1)+itemBaseWidth*j+itemBaseWidth*0.5,
                leftTopPos.y-rowInterval*(i+1)-itemBaseHeight*0.5-itemBaseHeight*i);//位置参数
                _itemBase.position=pos;//设置位置
                _itemBase.name=nameNumber.toString();
                this.itemBaseArray.push(_itemBase.getComponent(ItemBase));//把底座放入底座数组中
                nameNumber++;
            }
        }
    },
  
    /**
     * 合并信息保存在itemArray数组中最后保存在本地即可
     * @param {*} index 通过名称获取item是哪个底座
     * @param {*} gradeNum 通过gradeNum来设置当前item等级
     */
    setData(index,gradeNum){
        this.itemArray[Number(index)]=gradeNum;
        /**item数据添加到数据库 */
        this.updatePetList();//修改数据库宠物列表数据
    },
    /**
     * 初始化item对象池
     */
    initItemPool(){
        this.itemPool=new MyObjectPool(this.item,"Item",12);
    },
    /**
     * 获取对象池的item
     */
    getItem(){
        return this.itemPool.get();
    },
   /**
    * 回收到对象池
    * @param {*} item item
    */
    recycleItem(item) {
        this.itemPool.put(item);
    },
    /**
     * 添加item
     * @param {*} gradeNum 等级数
     * @param{*} goldLabel 商店创建金币label
     */
    addItemByGradeNum(gradeNum,goldLabel){
        let item=this.getBaseWithOutItem();//查找有没有底座
        if(item){//有底座
            var pet = this.findPetInfoByLevel(gradeNum);//当前等级宠物信息
            let goldGrade = pet.petGoldLevel>=this.maxGoldGrade?this.maxGoldGrade:pet.petGoldLevel;//计算金币等级（最高maxGoldGrade）
            let gold= goldGrade * pet.petGold;//计算金币
            if(userInfo.goldUsable >= gold){
                pet.petGoldLevel += 1;//金币等级+1(最大金币等级)
                goldGrade = pet.petGoldLevel>=this.maxGoldGrade?this.maxGoldGrade:pet.petGoldLevel;//重新计算金币等级（最高maxGoldGrade）
                userInfo.goldUsable=userInfo.goldUsable-gold;//可用金币减少
                 //修改宠物列表金币等级
                HttpHelper.request({
                    url: 'updatePetGoldLevel',
                    method: 'POST',
                    type: 'json',
                    data: {userId:userId,petInfoId:gradeNum,petGoldLevel:pet.petGoldLevel,goldUsable:userInfo.goldUsable-gold},
                    success: function(res) {
                        item.setItemByGradeNum(gradeNum);//创建宠物item
                        gold = goldGrade * pet.petGold;//重新计算金币
                        if(goldLabel!=null){//商店创建宠物金币不为空
                            goldLabel.string=this.currencyUnit(gold);//设置金币到商店创建金币label
                        }
                        if(maxGrade == gradeNum){//可创建等级=当前创建等级
                            Navigation.goldLabel.string=this.currencyUnit(gold);//设置金币到创建按钮金币label
                        }
                    }.bind(this),
                    erro: function(err) {
                        userInfo.goldUsable=userInfo.goldUsable+gold;//可用金币还原
                        Navigation.showHint("创建失败");//显示提示
                    } 
                });
                Header.setGold(userInfo.goldUsable);//设置金币
                return true;
            }else{
                Navigation.showHint("观看广告");//显示提示
                return false;
            }
        }else{
            Navigation.showHint("已经达到配置表中的合并上限");//显示提示
            return false;
        }
    },
    /**
     * 查找没有item的底座
     */
    getBaseWithOutItem(){//有的话返回当前底座  没有返回空
        return this.itemBaseArray.find(t=>{
            return (t.item==null);
        });
    },
    /**
     * 回收箱
     * @param {*} v2 
     */
    isDragToRecycle(v2){
        // 判断是否拖到了回收箱移出去
        if (this.recycleBox == null) {
            return false;
        }
        let box_bound = this.recycleBox.getBoundingBoxToWorld();
        return box_bound.x < v2.x && box_bound.y < v2.y && box_bound.x + box_bound.width > v2.x && box_bound.y + box_bound.height > v2.y;
    },
    /**
     * 拖拽到回收箱完成执行操作，可拓展
     * @param {*} dragingItem 
     */
    doneDragRecycle(dragingItem){
        dragingItem.removeItem();
        dragingItem.cleanGrageTag();
        dragingItem.setItem(null);
    },
     //获取数据配置表的例子，实际通过资源加载json获取数据,获取到的资源应在该脚本执行前加载完毕
    //示例中简单创建一份json，数据格式gradeNum必须有，其他可自行扩充
    getData(){
        let  data=[
            {"gradeNum":1,"img":"merge_1"},
            {"gradeNum":2,"img":"merge_2"},
            {"gradeNum":3,"img":"merge_3"},
            {"gradeNum":4,"img":"merge_4"},
            {"gradeNum":5,"img":"merge_5"},
            {"gradeNum":6,"img":"merge_6"},
            {"gradeNum":7,"img":"merge_7"},
            {"gradeNum":8,"img":"merge_8"},
            {"gradeNum":9,"img":"merge_9"},
            {"gradeNum":10,"img":"merge_10"},
            {"gradeNum":11,"img":"merge_11"},
            {"gradeNum":12,"img":"merge_12"},
            {"gradeNum":13,"img":"merge_13"},
            {"gradeNum":14,"img":"merge_14"},
            {"gradeNum":15,"img":"merge_15"},
            {"gradeNum":16,"img":"merge_16"},
            {"gradeNum":17,"img":"merge_17"},
            {"gradeNum":18,"img":"merge_18"},
            {"gradeNum":19,"img":"merge_19"},
            {"gradeNum":20,"img":"merge_20"},
            {"gradeNum":21,"img":"merge_21"},
            {"gradeNum":22,"img":"merge_22"},
            {"gradeNum":23,"img":"merge_23"},
            {"gradeNum":24,"img":"merge_24"},
            {"gradeNum":25,"img":"merge_25"},
            {"gradeNum":26,"img":"merge_26"},
            {"gradeNum":27,"img":"merge_27"},
            {"gradeNum":28,"img":"merge_28"},
            {"gradeNum":29,"img":"merge_29"},
            {"gradeNum":30,"img":"merge_30"},
            {"gradeNum":31,"img":"merge_31"},
            {"gradeNum":32,"img":"merge_32"},
            {"gradeNum":33,"img":"merge_33"},
            {"gradeNum":34,"img":"merge_34"},
            {"gradeNum":35,"img":"merge_35"},
            {"gradeNum":36,"img":"merge_36"},
            {"gradeNum":37,"img":"merge_37"},
        ];
        return data;
    },
    /**
     * 播放累计金币动画
     * @param {*} duration 时间
     */
    playGoldToDuration(duration){
        this.schedule(function() {
            let addGold=0;//累计金币总数
            for(var i=0;i<this.itemBaseArray.length;i++){
                let item = this.itemBaseArray[i].getItem();//获取底座上的宠物，然后播放动画
                if(item!=null){
                    let gradeNum=item.itemData.gradeNum;//当前item等级
                    let gold=25 * Math.pow(2,gradeNum-1);//计算金币（初始金币 + 2的等级-1次方）
                    let goldString='+' + this.currencyUnit(gold);//设置金币字符串
                    addGold=addGold + gold;//累计金币
                    item.playAnimation(goldString);//播放宠物加金币动画
                }
            }
            userInfo.goldUsable = Number(userInfo.goldUsable)  + addGold;//合计金币(原来可用金币+累计金币)
            userInfo.goldAll = Number(userInfo.goldAll) + addGold;//总金币（原来所有金币+累计金币）
            let profitSpeed=parseInt(addGold/duration);//收益速度(整数)
            Header.speedLabel.string=this.currencyUnit(profitSpeed) + " / s";//设置收益速度（累计金币/时间）
            HttpHelper.request({
                url: 'updateGameInfo',
                method: 'POST',
                type: 'json',
                data: {userId:userId,goldUsable:userInfo.goldUsable,profitSpeed:profitSpeed,goldAll:userInfo.goldAll},
                success: function(res) {
                    Header.setGold(userInfo.goldUsable);//设置金币
                }.bind(this),
                erro: function(err) {
                    console.log(err)
                } 
            });
        }, duration);
    },
     /**
     * 通过等级查询宠物信息
     */
    findPetInfoByLevel(petLevel){
        for(var i=0;i<petInfo.length;i++){
            if(petInfo[i].petLevel==petLevel){
                return petInfo[i];
            }
        }
    },
    /**
     * 格式化金币单位
     * @param {*} str 数字（金币）
     */
    currencyUnit(num){
        const units = ['K','M','B','T','aa','bb','cc','dd','ee','ff',
                    'GG','HH','II','JJ','KK','LL','MM','NN','OO'];
        num=num.toString();//数字转为字符串
        let strLength = num.length;
        if(strLength <= 3){
            return num;
        }else if(strLength>42){
            return 999+' max';
        }else{
            const unitIndex=Math.ceil(strLength/3)-2;//计算单位
            const unit=units[unitIndex];//获取单位
            const leftLength=strLength-(3 * (Math.ceil(strLength/3)-1));//小数点左边的长度
            var gold=num.substring(0,leftLength)+'.'+num.substring(leftLength,leftLength+2) + unit;
            return gold;
        }
    },
    /**
     * 修改数据库宠物列表信息
     */
    updatePetList(){
        var petList="";
        for(var i=0;i<this.itemArray.length;i++){
            if(i!=this.itemArray.length-1){
                petList=petList+this.itemArray[i]+",";
            }else{
                petList=petList+this.itemArray[i];
            }
        }
        HttpHelper.request({
            url: 'updateGameInfo',
            method: 'POST',
            type: 'json',
            data: {userId:userId,petList:petList},
            success: function(res) {
                
            }.bind(this),
            erro: function(err) {
                console.log(err)
            } 
        });
    },
    updateUserInfo(){
       
    }
});

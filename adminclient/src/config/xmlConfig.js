import axios from 'axios'
import x2js from 'x2js';
class xmlObj {
    constructor() {
    }
    async run() {
        let s = this;
        s.hasDone = false;
        let x2 = new x2js();
        await axios.get('../itemConfig.xml').then(res => {
            if (!res || !res.data) {
                return;
            }
            s.itemConfig = x2.xml2js(res.data).def;
        })
        await axios.get('../equipConfig.xml').then(res => {
            if (!res || !res.data) {
                return;
            }
            s.equipConfig = x2.xml2js(res.data).def;
        })
        await axios.get('../chopConfig.xml').then(res => {
            if (!res || !res.data) {
                return;
            }
            s.chopConfig = x2.xml2js(res.data).def;
        })
        await axios.get('../designationConfig.xml').then(res => {
            if (!res || !res.data) {
                return;
            }
            s.designationConfig = x2.xml2js(res.data).def;
        })
        await axios.get('../chengjiu.xml').then(res => {
            if (!res || !res.data) {
                return;
            }
            s.chengjiuConfig = x2.xml2js(res.data).def;
        });


        await axios.get('../sysCostItemLevelUpConfig.xml').then(res => {
            if (!res || !res.data) {
                return;
            }
            s.sysCostItemLevelUpConfig = x2.xml2js(res.data).def;
        });
        await axios.get('../sysAttributeContainerConfig.xml').then(res => {
            if (!res || !res.data) {
                return;
            }
            s.sysAttributeContainerConfig = x2.xml2js(res.data).def;
        })
        await axios.get('../sysCostItemAddExpConfig.xml').then(res => {
            if (!res || !res.data) {
                return;
            }
            s.sysCostItemAddExpConfig = x2.xml2js(res.data).def;
        })
        await axios.get('../sysCostItemLearnSkillConfig.xml').then(res => {
            if (!res || !res.data) {
                return;
            }
            s.sysCostItemLearnSkillConfig = x2.xml2js(res.data).def;
        })
        await axios.get('../sysGridConfig.xml').then(res => {
            if (!res || !res.data) {
                return;
            }
            s.sysGridConfig = x2.xml2js(res.data).def;
        });
        await axios.get('../rechargeConfig.xml').then(res => {
            if (!res || !res.data) {
                return;
            }
            s.rechargeConfig = x2.xml2js(res.data).def;
        });
        let obj1 = {};
        let obj2 = {};
        let itemArr = s.itemConfig.unit;

        let equipArr = s.equipConfig.unit;
        itemArr.forEach(el => {
            obj1[el._id] = el._name;
        })
        this.itemNameObj = obj1;
        equipArr.forEach(el => {
            obj2[el._id] = el._name;
        })
        this.equipNameObj = obj2;

        let obj3 = {};
        let chopArr = s.chopConfig.unit;
        chopArr.forEach(el => {
            obj3[el._id] = el._name;
        })
        this.chopNameObj = obj3;

        let obj4 = {};
        let designationArr = s.designationConfig.unit;
        designationArr.forEach(el => {
            obj4[el._id] = el._name;
        })
        this.designationObj = obj4;

        let obj5 = {};
        let chengjiuArr = s.chengjiuConfig.unit;
        chengjiuArr.forEach(el => {
            obj5[el._id] = el._desc;
        })
        this.chengjiuObj = obj5;
        s.hasDone = true;
    }
    /**
     * ??????????????????
     * @param {*} id 
     */
    async getChenjiuDes(id) {
        let s = this;
        if (s.hasDone == true) {
            if (!s.chengjiuObj) {
                await s.run();
                s.getChenjiuDes(id);
            }
            let des = s.chengjiuObj[id];
            if (!des) {
                console.error('============???????????????????????????id======\n', id);
                return id;
            }
            return des;

        } else {
            await s.run();
            s.getChenjiuDes(id);
        }
    }
    /**
     * ???????????????
     * @param {*} id 
     */
    async getChopName(id) {
        let s = this;
        if (s.hasDone == true) {
            if (!s.chopNameObj) {
                await s.run();
                s.getChopName(id);
            }
            let name = s.chopNameObj[id];
            if (!name) {
                console.error('============???????????????????????????id======\n', id);
                return id;
            }
            return name;

        } else {
            await s.run();
            s.getChopName(id);
        }
    }
    /**
     * ???????????????
     * @param {*} id 
     */
    async getDesignationName(id) {
        let s = this;
        if (s.hasDone == true) {
            if (!s.designationObj) {
                await s.run();
                s.getDesignationName(id);
            }
            let name = s.designationObj[id];
            if (!name) {
                console.error('============???????????????????????????id======\n', id);
                return id;
            }
            return name;

        } else {
            await s.run();
            s.getDesignationName(id);
        }
    }
    /**??????bones????????? */
    async getName(id) {
        let s = this;
        if (s.hasDone == true) {
            if (Number(id) >= 15001 && Number(id) <= 20000) {
                return s.equipNameObj[id];
            } else {
                let name = s.itemNameObj[id];
                if (!name) {
                    console.error('============???????????????????????????id======\n', id);
                    return id;
                }
                return name;
            }
        } else {
            await s.run();
            s.getName(id);
        }
    }
    /**??????bonus???????????????????????????????????? */
    async getBonus(bonus) {
        let s = this;
        if (s.hasDone == true) {
            if (!bonus) {
                return null;
            }
            if (Number(bonus.id) >= 15001 && Number(bonus.id) <= 20000) {
                return s.equipNameObj[bonus.id + ''] + ':' + bonus.count;
            } else {
                let name = s.itemNameObj[bonus.id + ''];
                if (!name) {
                    console.error('============???????????????????????????id======\n', bonus.id);
                    return bonus.id + ':' + bonus.count;
                }
                return name + ':' + bonus.count;
            }
        } else {
            await s.run();
            s.getBonus(bonus);
        }
    }
    /**??????reward???????????????????????????????????? */
    async getReward(bonus) {
        let s = this;
        if (s.hasDone == true) {
            if(!bonus) {
                return null;
            }
            if (Number(bonus.id) >= 15001 && Number(bonus.id) <= 20000) {
                return s.equipNameObj[bonus.id + ''] + ':' + bonus.num;
            } else {
                let name = s.itemNameObj[bonus.id + ''];
                if (!name) {
                    console.error('============???????????????????????????id======\n', bonus.id);
                    return bonus.id + ':' + bonus.num;
                }
                return name + ':' + bonus.num;
            }
        } else {
            await s.run();
            s.getReward(bonus);
        }
    }
    /**??????id??????configName */
    getConfigName(config, id) {
        let item = null;
        config.unit.forEach(el => {
            if (el._id == id) {
                item = el;
                return;
            }
        });
        let name = item ? `${item._name}(${id})` : id
        return name;
    };
    /**??????config??? id ???????????????????????? */
    getConfigNameByIdConfigName(_name,id) {
        let item = null;
        s[_name].unit.forEach(el => {
            if (el._id == id) {
                item = el;
                return;
            }
        });
        let name = item ? `${item._name}(${id})` : id
        return name;
    }
    /**??????????????????????????? */
    async getRechargeNameById(id) {
        let s = this;
        if (s.hasDone == true) {
            return s.getConfigName(s.rechargeConfig, id);
        } else {
            await s.run();
            s.getRechargeNameById(id);
        }
    }
    /**?????????????????????????????????id??????????????? */
    async getHuoBanName(opValue, configId, levelId, childConfigId) {
        let s = this;
        let obj = {};
        if (s.hasDone == true) {
            switch (opValue) {
                case 0:
                    obj.configName = s.getConfigName(s.sysAttributeContainerConfig, configId);
                    obj.levelName = s.getConfigName(s.sysAttributeContainerConfig, levelId);
                    obj.opName = '?????????';
                    break;
                case 1:
                    obj.configName = s.getConfigName(s.sysAttributeContainerConfig, configId);
                    obj.levelName = s.getConfigName(s.sysAttributeContainerConfig, levelId);
                    obj.childConfigName = s.getConfigName(s.sysCostItemAddExpConfig, childConfigId);
                    obj.opName = '???????????????,?????????-?????????????????????';
                    break;
                case 2:
                    obj.configName = s.getConfigName(s.sysCostItemLevelUpConfig, configId);
                    obj.levelName = s.getConfigName(s.sysCostItemLevelUpConfig, levelId);
                    obj.opName = '??????????????????, ????????????';
                    break;
                case 3:
                    obj.configName = s.getConfigName(s.sysCostItemAddExpConfig, configId);
                    obj.levelName = s.getConfigName(s.sysCostItemAddExpConfig, levelId);
                    obj.opName = '???????????????';
                    break;
                case 4:
                    obj.configName = s.getConfigName(s.sysCostItemLearnSkillConfig, configId);
                    obj.levelName = s.getConfigName(s.sysCostItemLearnSkillConfig, levelId);
                    obj.opName = '????????????????????????';
                    break;
                case 5:
                    obj.configName = s.getConfigName(s.sysGridConfig, configId);
                    obj.levelName = s.getConfigName(s.sysGridConfig, levelId);
                    obj.opName = '????????????????????????';
                    break;
                case 6:
                    obj.configName = s.getConfigName(s.sysGridConfig, configId);
                    obj.levelName = s.getConfigName(s.sysGridConfig, levelId);
                    obj.opName = '?????????????????????';
                    break;
            }
            return obj;
        } else {
            await s.run();
            s.getHuoBanName(id, opValue)
        }
    }
}
var xml = new xmlObj();
export default xml;

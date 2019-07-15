# OBD_CAR
 VIN的获取AT指令是0902(国标，但是有些车型有定制)
 
 
* AT H0,H1 :(header)0是off，1是on
* AT M0,M1 :（memeroy）0是off，1是on
* AT D : 恢复到自定义
* AT Z  :所有重置
* AT SP 0 ：随机帧，自动的，目前知道5,6,7
* AT SH 7DF  :是选择can500 波段11位的
* AT SH DB 33 F1 :是选择can500 波段29位的

# 技术点
 1. 蓝牙建立soket,看代码bt_client 和bt_server部分
 ![image](https://github.com/laiyuchenrushuang/OBD_CAR/blob/master/lizi.png)

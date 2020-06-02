# OBD_CAR
 VIN的获取AT指令是0902(国标，但是有些车型有定制)
 
 
* AT H0,H1 :(header)0是off，1是on
* AT M0,M1 :（memeroy）0是off，1是on
* AT D : 恢复到自定义
* AT Z  :所有重置
* AT SP 0 ：随机帧，自动的，目前知道5,6,7
* AT SH 7DF  :是选择can500 波段11位的
* AT SH DB 33 F1 :是选择can500 波段29位的
* AT DP ： 查看波段

* 工作记录：  
----2019----  
1. 0716 武侯机场路建国  重置vin,自动寻址（Header），寻波段
2. 0723 麓山建国 Jeep车 寻址： 18 DA 10 F1，读取vin指令：22 F1 90
3. 0805 新都标致 308车  问题是09 02或者22 F1 90返回的490201和490211 62F190 三位截取掉前面3个16位，就是6个字符，去掉  
----2020----  
1. 0602 建国福田4S店  OBD读取通过0902无法读取，需要选择哈佛车型， AT SP 6 ， 22 F1 90，去获取VIN  

# 技术点
 *  蓝牙建立soket,看代码bt_client 和bt_server部分
 *  ![image](https://github.com/laiyuchenrushuang/OBD_CAR/blob/master/lizi.png)
 
 

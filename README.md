# Chocolate

A Minecraft 1.8.9 Chinese PVP Client based on Tritium

## 源代码感谢

感谢[GitHub - ImFl0wow/Tritium-Open](https://github.com/ImFl0wow/Tritium-Open) 源代码

感谢[GitHub - Canelex/DragonWingsMod](https://github.com/Canelex/DragonWingsMod) 龙翼组件

## 为什么改名叫Chocolate？

单纯觉得好听一些

## Chocolate修改了什么？

1. 修改了启动画面，使用百分比布局替代了原本不合理的绝对参数，添加了启动进度条文字描述。

2. 删除了第一次进入游戏的语言设置，默认为简体中文。

3. 修改了游戏主界面的标题文字，去掉了Welcome提示和下方的作者信息(但是预计在之后更新专门添加一个不减暂时作者信息)；修改了四个主要按钮的位置，同样使用了相对式参数来保证不会出现全屏或者小窗口布局错误。

4. 对部分入口按钮进行微调，去掉了游戏内登录的功能(现在都是微软登录这个东西意义真不大，而且容易被说后门)。

5. 修改了版本json文件中authlib.jar文件来源(与原版1.8.9一致)，也算是去掉一个诟病后面很久的设计。版本json文件附在仓库中

6. 修复了Apache_log4j2(CVE-2021-44228)漏洞，见[知乎 [漏洞复现]Apache_log4j2（CVE-2021-44228）_](https://zhuanlan.zhihu.com/p/462419319)，[Minecraft官网公告](https://www.minecraft.net/en-us/article/important-message--security-vulnerability-java-edition)

## 使用方法

1.参考MCP项目mcp918的方式获取运行库文件和依赖库文件

2.使用IDEA导入本项目，并在项目结构中设置依赖库

3.根目录下新建run目录，在run目录下新建natives文件夹，在natives文件夹下放置原本在版本文件夹下的.dll动态库文件(可以在运行配置中查看原因)

4.按照需求进行调试或者构建

## Chocolate还在修复的问题

1.找不到可以正常启动的主类位置：因为没有注释的原因，个人推测其存在参数特殊处理的机制，已知根目录下*Start*启动无需多余参数但是不能正版登录，带参数启动会出现参数过多报错，*tritium.launch.Main*(同时也是其版本json文件中声明的主类)带参数启动正常，同时存在*tritium.development.RunClient*，因为没有注释我也看不懂什么机制。以上三个位置启动都会导致在PCL2启动右Shift无法打开GUI问题。

2.添加了龙翼组件：但是因为没有注释看不懂其模块加载和调用问题，当前进度是模块类中构造函数和渲染方法都无法调用。

## Chocolate未来会更新的内容

1.修复以上所说的内容

2.对右ShiftUI进行重构美化(其实已经改过一次，但是没有注释四个xy参数看不出来哪个是关于position的，调试半天搞炸了): )

3.发布release

## 作者的话

1.8.9PVP早就走下坡路了，改这个端无非实现很久之前的一个愿望而已。

package me.imflowow.tritium.utils.language.lang;

import java.util.HashMap;

import com.mojang.authlib.exceptions.AuthenticationException;
import me.imflowow.tritium.utils.account.GuiAccountManager;
import tritium.api.module.value.impl.BooleanValue;

public class ChineseLanguage implements Language {

	public HashMap<String, String> load() {
		HashMap<String, String> langMap = new HashMap();

		//2024.2.23 Howxu additions
		langMap.put("Quit","退出");
//		langMap.put("Tritium", "氚");

		langMap.put("§eWarning: You are using Beta Edition.", "§e警告：你正在使用测试版本。");
		langMap.put("§cWarning: You are using Development Edition.", "§c警告：你正在使用开发版本。");
		langMap.put("Contribution by ", "贡献者：");

		langMap.put("Single Player", "单人游戏");
		langMap.put("Multi Player", "多人游戏");
		langMap.put("Options", "设置");

		langMap.put("ClearChat", "去除聊天背景");
		langMap.put("AntiSpam", "反刷屏");
		langMap.put("Chat", "聊天栏设置");
		langMap.put("Some changes of chat.", "一些聊天栏的设置");

		langMap.put("ContainerAnimations", "背包动画");
		langMap.put("A super cool animations when you open a caontainer", "当你打开背包的时候会有一个超级酷酷酷酷酷酷的动画！");

		langMap.put("ScreenBackground", "菜单背景");
		langMap.put("Show screens' background.", "显示你的菜单背景");

		langMap.put("ArmorDisplay", "装备显示");
		langMap.put("Display armors on your screen.", "在你的图形用户界面中显示你的装备");
		langMap.put("Damage", "损坏度");
		langMap.put("Healmet", "头盔");
		langMap.put("Breastplate", "胸甲");
		langMap.put("Trousers", "护腿");
		langMap.put("Shoes", "靴子");
		langMap.put("Held", "手持物品");

		langMap.put("AutoGG", "自动GoodGame");
		langMap.put("When the game has been over on Hypixel,it will send a message\"gg\"",
				"当你在hypixel小游戏服务器中结束一场游戏的时候自动发送GG");
		langMap.put("You will start the next game in ", "下局游戏开始 还有");
		langMap.put("Delay", "延迟");
		langMap.put("AutoPlay", "自动开局");

		langMap.put("BlockAnimations", "防砍动画");
		langMap.put("Change your blocking animations", "改变你的防砍动画");
		langMap.put("SwingSpeed", "挥手速度");
		langMap.put("AnimRotX", "X轴旋转动画");
		langMap.put("AnimRotY", "Y轴旋转动画");
		langMap.put("AnimRotZ", "Z轴旋转动画");
		langMap.put("RotX", "X轴旋转角度");
		langMap.put("RotY", "Y轴旋转角度");
		langMap.put("RotZ", "Z轴旋转角度");
		langMap.put("X", "X轴偏移");
		langMap.put("Y", "Y轴偏移");
		langMap.put("Z", "Z轴偏移");
		langMap.put("AnimX", "X轴平移动画");
		langMap.put("AnimY", "Y轴平移动画");
		langMap.put("AnimZ", "Z轴平移动画");
		langMap.put("HoldingX", "手持X轴偏移");
		langMap.put("HoldingY", "手持Y轴偏移");
		langMap.put("HoldingZ", "手持Z轴偏移");
		langMap.put("Mode", "模式");

		langMap.put("BlockOverlay", "方块边框");
		langMap.put("The block you are pointing will be rendered brightly.", "鼠标指针处的方块会有边框");
		langMap.put("Rect", "方框");
		langMap.put("Outline", "边框");
		langMap.put("lineWidth", "边框粗细");
		langMap.put("RectColor", "方框颜色");
		langMap.put("OutlineColor", "边框颜色");

		langMap.put("ChatCopy", "复制聊天信息");
		langMap.put("It allowes you to copy chat message when you click \"[C]\"", "按下聊天栏后面的\"[C]\"就会复制到剪贴板");
		langMap.put("Click on this to copy this message.", "点击拷贝该文本");

		langMap.put("TextColor", "文本颜色");
		langMap.put("BackgroundColor", "背景颜色");

		langMap.put("ClockDisplay", "时间显示");
		langMap.put("Display your clock on your screen.", "显示你的时间。");

		langMap.put("CoordDisplay", "坐标显示");
		langMap.put("Display your Coord on your screen.", "显示你的坐标。");

		langMap.put("CPSDisplay", "每秒点击次数显示");
		langMap.put("Display your CPS on your screen.", "显示你的每秒点击次数。");

		langMap.put("Crosshair", "自定义准星");
		langMap.put("Make your crosshair great again!", "使你的准星再次变得伟大！");
		langMap.put("CrosshairColor", "准星颜色");
		langMap.put("DotColor", "中点颜色");
		langMap.put("OutlineColor", "边框颜色");
		langMap.put("Dot", "中点显示");
		langMap.put("Outline", "边框显示");
		langMap.put("Size", "大小");
		langMap.put("DotSize", "中点大小");
		langMap.put("OutlineSize", "边框大小");
		langMap.put("Width", "宽度");
		langMap.put("Height", "高度");
		langMap.put("Gap", "空隙长度");
		langMap.put("Animation", "动画");
		langMap.put("AnimationGap", "动画平移长度");
		langMap.put("AnimationSpeed", "动画速度");

		langMap.put("CustomParticles", "自定义粒子颜色");
		langMap.put("You can make your particles colorful.", "使你的粒子颜色多姿多彩！");
		langMap.put("Color", "颜色");

		langMap.put("TextColor", "文本颜色");
		langMap.put("BackgroundColor", "背景颜色");
		langMap.put("BackgroundRectColor", "背景方框颜色");
		langMap.put("Animation", "动画");
		langMap.put("AnimationColor", "动画颜色");
		langMap.put("RectColor", "方框颜色");
		langMap.put("TargetColor", "目标颜色");
		langMap.put("SelfColor", "自身颜色");
		langMap.put("DuelInfo", "单挑决斗信息");
		langMap.put("While pvp in solo,you can use it to display your pvp information.", "当你在和单个玩家决斗时，这个会记录你与对方的信息。");

		langMap.put("EnchantEffect", "自定义附魔颜色");
		langMap.put("You can make your enchant effect colorful.", "使你的附魔颜色多姿多彩！");

		langMap.put("FPSDisplay", "每秒帧数显示");
		langMap.put("Display your FPS on your screen.", "显示你的每秒帧数");

		langMap.put("Fullbright", "夜视");
		langMap.put("Make the world bright!", "青光眼！");

		langMap.put("ItemPhysic", "物理掉落物");
		langMap.put("Just ItemPhysic.", "当你丢出去一个物品时，掉落物会收到重力影响。");

		langMap.put("KeyStrokes", "按键显示");
		langMap.put("When you press down a key,it can show you what you press down.", "按下一个按键，会在图像用户界面中显示你按了什么按键。");
		langMap.put("Mouse", "显示鼠标按键");
		langMap.put("CPS", "显示每秒点击次数");
		langMap.put("Jump", "显示跳跃按键");
		langMap.put("RectColor", "方框颜色");
		langMap.put("ClickDownColor", "按住时方框颜色");
		langMap.put("TextColor", "文本颜色");
		langMap.put("AnimationSpeed", "动画速度");

		langMap.put("LowFire", "低火");
		langMap.put("Make the fire down!", "火焰无法阻挡你的视野！");
		langMap.put("PositionY", "偏移Y轴量");

		langMap.put("MoreParticles", "更多的打击粒子");
		langMap.put("When you attack a player,there will be more partcles of attacking.", "当你打人的时候会有更多的粒子");
		langMap.put("CrackSize", "打击粒子数量");
		langMap.put("CritParticle", "更多的暴击粒子");
		langMap.put("NormalParticle", "更多的普通粒子");

		langMap.put("MotionBlur", "动态模糊");
		langMap.put("Make your screen look high in frames.", "哇，我的帧数变高了！");
		langMap.put("FrameMultiplier", "补帧时间");

		langMap.put("OldAnimations", "1.7旧动画");
		langMap.put("Make your animation look like older version.", "让你的动画看起来像旧版本一样");
		langMap.put("DebugRender", "旧版F3+B");
		langMap.put("RodScale", "旧版鱼竿大小");
		langMap.put("BowScale", "旧版弓大小");
		langMap.put("RodPositions", "旧版鱼竿位置");
		langMap.put("BowPositions", "旧版弓位置");
		langMap.put("HurtAnimations", "旧版血量闪烁");
		langMap.put("DamageBright", "旧版受伤高亮");
		langMap.put("ItemPosition", "旧版物品模型位置");
		langMap.put("BlockPosition", "旧版格挡模型位置");
		langMap.put("Sneak", "下蹲动画");

		langMap.put("Perspective", "360度视角");
		langMap.put("You know it is 360° you can be in fov.", "360度视角");
		langMap.put("Click", "单击");
		langMap.put("Hold", "按住");

		langMap.put("Rainbow", "彩色");
		langMap.put("RainbowSpeed", "彩色渐变速度");

		langMap.put("PingDisplay", "延迟显示");
		langMap.put("Display your Ping on your screen.", "显示你的延迟");

		langMap.put("PotionDisplay", "药水效果显示");
		langMap.put("Display the potions effect you have on you screen.", "在你的图形用户界面中显示你的药水效果");

		langMap.put("ReachDisplay", "攻击距离显示");
		langMap.put("Display your attack distance on your screen", "在你的图形用户界面中显示你的攻击距离");

		langMap.put("SelfHealth", "自身血量");
		langMap.put("Display your health on your screen.", "在你的图形用户界面中显示你的血量");
		langMap.put("HealthColor", "血量颜色");
		langMap.put("HealthShadowColor", "阴影颜色");
		langMap.put("BackgroundColor", "背景颜色");
		langMap.put("AnimationSpeed", "动画速度");

		langMap.put("Sprint", "自动疾跑");
		langMap.put("Keep sprint!", "帮你按疾跑键啦！");

		langMap.put("WorldTime", "更改世界时间");
		langMap.put("Change the world time.", "更改你的世界时间！");
		langMap.put("Time", "时间");

		langMap.put("Target not found...", "没有找到目标...");
		langMap.put("Reach Display", "攻击距离");

		langMap.put("Edit", "编辑");
		langMap.put("Global", "全局");
		langMap.put("Modules", "功能");
		// TODO 20210516
		langMap.put(": press any key you want to bind to", "：任意按下一个你想绑定的按键。");
		langMap.put("That module is unbindable.", "该功能无法绑定按键。");
		// TODO 20210522

		langMap.put("Alt Repository", "账号管理");
		// TODO 20210530

		langMap.put("Success.", "成功。");
		langMap.put("The username is filled in incorrectly. It must start with 6-16 letters or numbers!",
				"用户名填写错误,必须以字母开头6-16位字母或数字!");
		langMap.put("Please input 6-16 password!", "密码填写错误,请输入6-16位密码！");
		langMap.put("Please input email correctly.", "邮箱填写错误,请正确输入邮箱！.");
		langMap.put("The username already exists.", "用户名已存在。");
		langMap.put("Wrong username or password.", "用户名或密码错误。");
		langMap.put("User does not exist.", "用户不存在。");
		langMap.put("username", "用户名");
		langMap.put("password", "密码");
		langMap.put("Login", "登录");
		langMap.put("§nChangePassword?", "§n修改密码？");
		langMap.put("§nRegister", "§n注册账号");
		langMap.put("Register", "注册");
		langMap.put("mail", "邮箱");
		langMap.put("new password", "新密码");
		langMap.put("Change Password", "修改密码");
		langMap.put("§cDeveloper", "§c开发者");
		langMap.put("§eStreamer", "§e主播");
		langMap.put("§3Donator", "§3捐赠者");
		langMap.put("User", "用户");
		// TODO 20210605
		langMap.put("ChunkAnimator", "区块加载动画");
		langMap.put("Mum!I have ChunkAnimator!", "妈妈！我的区块在飞！");
		langMap.put("AnimatedView", "动态视图");
		langMap.put("The Tooltips will follow with your rotation.", "你的游戏UI会随着你的视角移动而移动。");
		// TODO 20210626
		langMap.put("This module has not been bound.(middle click on it to bind)", "这个功能还没有绑定按键(请按鼠标中键绑定)");
		langMap.put("DamageDealt", "血量伤害信息粒子");
		langMap.put("Display Damage Dealt", "当一个生物回血或收到伤害时，会显示改变的血量。");
		langMap.put("CustomScoreboard","自定义积分板");
		langMap.put("You may be with intellectual disabilities if you even don't know what it is.","如果你连这都不知道，摸摸你的脑袋瓜。");
		langMap.put("Hidden","隐藏");
		langMap.put("ClearBackground","隐藏背景");
		langMap.put("HideRedNumber","隐藏红色数字");
		// TODO 20210627
		langMap.put("§aLogged in.", "§a登录成功...");
		langMap.put("§cLogin failed!", "§c登录失败...");
		langMap.put("Logging in...", "登录中...");
		langMap.put("§cPlease set the mode to \"Netease\" first.","§c请先将模式调至 \"Netease\"");
		langMap.put("§aSuccessfully get netease username...", "§a成功获取网易ID...");
		// TODO 20210628
		langMap.put("I can speak 24 languages.", "我是翻译学家");
		langMap.put("Translator","自动翻译");
		langMap.put("Click on this to translate this message.","点击翻译该文本");
		//TODO 20210701
		langMap.put("", "");
		return langMap;
	}

}

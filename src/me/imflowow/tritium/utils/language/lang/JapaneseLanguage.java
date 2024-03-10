package me.imflowow.tritium.utils.language.lang;

import java.util.HashMap;

public class JapaneseLanguage implements Language{
	
	public HashMap<String,String> load()
	{
		HashMap<String, String> langMap = new HashMap();
//		langMap.put("Tritium", "氚");

		langMap.put("§eWarning: You are using Beta Edition.", "§e警告：テストバージョンを使っています。");
		langMap.put("§cWarning: You are using Development Edition.", "§c警告：開発バージョンを使っています。");
		// TODO MAIN MENU
		langMap.put("Contribution by ", "貢献者：");
		langMap.put("Single Player", "シングルプレイ");
		langMap.put("Multi Player", "マルチプレイ");
		langMap.put("Options", "設定");

		langMap.put("ClearChat", "チャットの背景を削除");
		langMap.put("AntiSpam", "アンチ スクリーン");
		langMap.put("Chat", "チャットバー設定");
		langMap.put("Some changes of chat.", "チャットの変更");

		langMap.put("ContainerAnimations", "コンテナ アニメーション");
		langMap.put("A super cool animations when you open a caontainer", "コンテナを開けたら、超クールでクールなアニメーションがあります！");

		langMap.put("ScreenBackground", "スクリーン背景");
		langMap.put("Show screens' background.", "スクリーン背景を表示する");

		langMap.put("ArmorDisplay", "装甲ディスプレイ");
		langMap.put("Display armors on your screen.", "画面上に武器を表示する");
		langMap.put("Damage", "耐久性");
		langMap.put("Healmet", "ヘルメット");
		langMap.put("Breastplate", "チェストプレート");
		langMap.put("Trousers", "レギンス");
		langMap.put("Shoes", "ブーツ");
		langMap.put("Held", "所持品");

		langMap.put("AutoGG", "自動GoodGame");
		langMap.put("When the game has been over on Hypixel,it will send a message\"gg\"", "hypixelサーバーの中で1度のゲームを終える時自動でGGを送る");
		langMap.put("You will start the next game in ", "次のゲームが始まるまであと です");
		langMap.put("Delay", "遅延");
		langMap.put("AutoPlay", "自動開局");

		langMap.put("BlockAnimations", "ブロック アニメーション");
		langMap.put("Change your blocking animations", "ブロック アニメーションを変更する");
		langMap.put("SwingSpeed", "手を振るスピード");
		langMap.put("AnimRotX", "X軸回転アニメーション");
		langMap.put("AnimRotY", "Y軸回転アニメーション");
		langMap.put("AnimRotZ", "Z軸回転アニメーション");
		langMap.put("RotX", "X軸回転角度");
		langMap.put("RotY", "Y軸回転角度");
		langMap.put("RotZ", "Z軸回転角度");
		langMap.put("X", "X軸オフセット");
		langMap.put("Y", "Y軸オフセット");
		langMap.put("Z", "Z軸オフセット");
		langMap.put("AnimX", "X軸移動アニメーション");
		langMap.put("AnimY", "Y軸移動アニメーション");
		langMap.put("AnimZ", "Z軸移動アニメーション");
		langMap.put("HoldingX", "ハンドX軸オフセット");
		langMap.put("HoldingY", "ハンドY軸オフセット");
		langMap.put("HoldingZ", "ハンドZ軸オフセット");
		langMap.put("Mode", "モード");

		langMap.put("BlockOverlay", "ブロック オーバレイ");
		langMap.put("The block you are pointing will be rendered brightly.", "マウスポインタのブロックにオーバレイがあり");
		langMap.put("Rect", "長方形");
		langMap.put("Outline", "輪郭");
		langMap.put("lineWidth", "輪郭の太さ");
		langMap.put("RectColor", "長方形の色");
		langMap.put("OutlineColor", "輪郭の色");

		langMap.put("ChatCopy", "チャット記録をコピー");
		langMap.put("It allowes you to copy chat message when you click \"[C]\"", "チャットバーの後ろの\"[C]\"を押したらクリップボードにコピーされる");
		langMap.put("Click on this to copy this message.", "クリックしてテキストをコピー");
		
		langMap.put("TextColor", "テキストの色");
		langMap.put("BackgroundColor", "背景の色");
		
		langMap.put("ClockDisplay", "時刻表示");
		langMap.put("Display your clock on your screen.", "時刻を表示。");
		
		langMap.put("CoordDisplay", "座標表示");
		langMap.put("Display your Coord on your screen.", "あなたの座標を表示する。");
		
		langMap.put("CPSDisplay", "毎秒クリック数表示");
		langMap.put("Display your CPS on your screen.", "毎秒クリック数を表示する。");
		
		langMap.put("Crosshair", "カスタム準星");
		langMap.put("Make your crosshair great again!", "あなたの準星を再び偉大にする！");
		langMap.put("CrosshairColor", "準星の色");
		langMap.put("DotColor", "中点色");
		langMap.put("OutlineColor", "輪郭の色");
		langMap.put("Dot", "中点显示");
		langMap.put("Outline", "中点表示");
		langMap.put("Size", "サイズ");
		langMap.put("DotSize", "中点サイズ");
		langMap.put("OutlineSize", "輪郭サイズ");
		langMap.put("Width", "幅");
		langMap.put("Height", "高さ");
		langMap.put("Gap", "ボイド長さ");
		langMap.put("Animation", "アニメーション");
		langMap.put("AnimationGap", "アニメーションの長さをずらす");
		langMap.put("AnimationSpeed", "アニメーションの速度");
		
		langMap.put("CustomParticles", "カスタム粒子の色");
		langMap.put("You can make your particles colorful.", "あなたの粒子の色を多彩にする！");
		langMap.put("Color", "色");
		
		langMap.put("TextColor", "テキストの色");
		langMap.put("BackgroundColor", "背景の色");
		langMap.put("BackgroundRectColor", "背景の長方形の色");
		langMap.put("Animation", "アニメーション");
		langMap.put("AnimationColor", "アニメーションの色");
		langMap.put("RectColor", "長方形の色");
		langMap.put("TargetColor", "ターゲットの色");
		langMap.put("SelfColor", "自分の色");
		langMap.put("DuelInfo", "シングル決闘情報");
		langMap.put("While pvp in solo,you can use it to display your pvp information.", "あなたが一人のプレイヤーと決闘する時、これはあなたと相手の情報を記録。");
		
		langMap.put("EnchantEffect", "シングル エンチャントの色");
		langMap.put("You can make your enchant effect colorful.", "あなたのエンチャントの色を多彩にする！");
		
		langMap.put("FPSDisplay", "毎秒フレーム数表示");
		langMap.put("Display your FPS on your screen.", "毎秒フレーム数を表示する。");
		
		langMap.put("Fullbright", "夜見");
		langMap.put("Make the world bright!", "世界を明るくする！");
		
		langMap.put("ItemPhysic", "物理落下物");
		langMap.put("Just ItemPhysic.", "一つのものをなくしたら、落としたものは重力の影響を受ける。");
		
		langMap.put("KeyStrokes", "キー表示");
		langMap.put("When you press down a key,it can show you what you press down.", "キーを押すと、どのキーを押したかが画面に表示される。");
		langMap.put("Mouse", "マウスボタン表示");
		langMap.put("CPS", "秒のクリック数を表示する");
		langMap.put("Jump", "ジャンプキーを表示");
		langMap.put("RectColor", "長方形の色");
		langMap.put("ClickDownColor", "押した時，長方形の颜色");
		langMap.put("TextColor", "テキストの色");
		langMap.put("AnimationSpeed", "アニメーションの速度");
		
		langMap.put("LowFire", "弱い火");
		langMap.put("Make the fire down!", "炎はあなたの視界を遮ることができません！");
		langMap.put("PositionY", "Y軸オフセット");
		
		langMap.put("MoreParticles", "もっと多くの打撃粒子");
		langMap.put("When you attack a player,there will be more partcles of attacking.", "人を殴るともっと多くの粒子がある");
		langMap.put("CrackSize", "打撃粒子数");
		langMap.put("CritParticle", "もっと多くのクリティカル粒子");
		langMap.put("NormalParticle", "もっと多くの普通の粒子");
		
		langMap.put("MotionBlur", "モーションブラー");
		langMap.put("Make your screen look high in frames.", "わ、フレーム数が高くなった");
		langMap.put("FrameMultiplier", "フレーム時間");
		
		langMap.put("OldAnimations", "1.7古いアニメーション");
		langMap.put("Make your animation look like older version.", "あなたのアニメーションを古いバージョンのように見せてください。");
		langMap.put("DebugRender", "旧版のF3+B");
		langMap.put("RodScale", "旧版の釣り竿サイズ");
		langMap.put("BowScale", "旧版の弓のサイズ");
		langMap.put("RodPositions", "旧版の釣り竿の位置");
		langMap.put("BowPositions", "旧版弓の位置");
		langMap.put("HurtAnimations", "旧版の血の点滅");
		langMap.put("DamageBright", "旧版の傷はハイライト");
		langMap.put("ItemPosition", "旧版のモデルの位置");
		langMap.put("BlockPosition", "旧版ギブロックアニメの位置");
		langMap.put("Sneak", "しゃがみアニメ");
		
		langMap.put("Perspective", "360度の画角");
		langMap.put("You know it is 360° you can be in fov.", "360度の画角");
		langMap.put("Click", "クリック");
		langMap.put("Hold", "ホールド");
		
		langMap.put("Rainbow", "虹");
		langMap.put("RainbowSpeed", "カラーグラデーション速度");
		
		langMap.put("PingDisplay", "遅延表示");
		langMap.put("Display your Ping on your screen.", "遅延を表示する。");
		
		langMap.put("PotionDisplay", "目薬の効果表示");
		langMap.put("Display the potions effect you have on you screen.", "あなたの画面にに目薬の効果を表示する");
		
		langMap.put("ReachDisplay", "攻撃距離表示");
		langMap.put("Display your attack distance on your screen", "あなたの画面に攻撃距離を表示する");
		
		langMap.put("SelfHealth", "自分の血の量");
		langMap.put("Display your health on your screen.", "あなたの画面にあなたの血の量を表示する");
		langMap.put("HealthColor", "血の色");
		langMap.put("HealthShadowColor", "影の色");
		langMap.put("BackgroundColor", "背景の色");
		langMap.put("AnimationSpeed", "アニメーションの速度");
		
		langMap.put("Sprint", "自動疾走");
		langMap.put("Keep sprint!", "走るキーを押してあげる！");
		
		langMap.put("WorldTime", "カスタム世界時間");
		langMap.put("Change the world time.", "世界時間を変更する");
		langMap.put("Time", "時間");
		
		langMap.put("Target not found...", "目標が見つからなかった...");
		langMap.put("Reach Display", "攻撃距離");
		
		langMap.put("Edit", "編集");
		langMap.put("Global", "グローバル");
		langMap.put("Modules", "モジュール");
		//TODO 20210516
		
		return langMap;
	}

}

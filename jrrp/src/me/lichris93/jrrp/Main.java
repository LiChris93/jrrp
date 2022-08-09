package me.lichris93.jrrp;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.annotation.ParametersAreNonnullByDefault;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent;

public class Main extends JavaPlugin {
	long loadtime;
	HashMap<Long, String[]> Time = new HashMap<Long, String[]>();
	FileConfiguration config = this.getConfig();
	long qqbot;
	long qqgroup;
	String version;
	String admin;
	String jrrpmes;
	String jrrpclear;
	String sendmap;
	String getfailmes;
	String getsucceedmes;
	Set<String> list = new HashSet<String>();

	@Override
	public void onEnable() {
		saveDefaultConfig();
		loadtime = System.currentTimeMillis();
		getLogger().info("-------------------------------------");
		getLogger().info(" L      IIIII   999999999  333333333 ");
		getLogger().info(" L        I     9       9          3 ");
		getLogger().info(" L        I     9       9          3 ");
		getLogger().info(" L        I     999999999  333333333 ");
		getLogger().info(" L        I             9          3 ");
		getLogger().info(" L        I             9          3");
		getLogger().info(" L        I     9       9          3");
		getLogger().info(" LLLLLL IIIII   999999999  333333333 ");
		getLogger().info("-------------------------------------");
		Bukkit.getPluginManager().registerEvents(new onJrrp(), this);
		getLogger().info("事件监听器注册完成");
		Bukkit.getPluginCommand("jrrp").setExecutor(new jrrpcom());
		getLogger().info("命令执行器注册完成");
		qqbot = config.getLong("bot");
		qqgroup = config.getLong("group");
		admin = config.getString("admin");
		jrrpmes = config.getString("lang.jrrpmes");
		version = config.getString("version");
		jrrpclear = config.getString("lang.jrrpclear");
		sendmap = config.getString("lang.sendmap");
		getfailmes = config.getString("lang.getfailmes");
		getsucceedmes = config.getString("lang.getsucceedmes");
		if (admin.contains(",")) {
			String[] temp = admin.split(",");
			for (String i : temp) {
				list.add(i);
			}
		} else {
				list.add(admin);
		}
		getLogger().info("config读取完成");
		getLogger().info("jrrp 加载完成！——By LiChris93[" + (System.currentTimeMillis() - loadtime) + "ms]");

	}

	@Override
	public void onDisable() {
		getLogger().info("jrrp 卸载完毕！——By LiChris93");
	}

	public class onJrrp implements Listener {

		@EventHandler
		public void onsend(MiraiGroupMessageEvent e) {

			if (e.getMessage().equals(jrrpmes)) {
				if (Time.get(e.getSenderID()) == null) {// 判断hashmap中是否存在发送者，没有则发送消息并存储

					Date now = new Date();
					SimpleDateFormat f = new SimpleDateFormat("yyyy 年 MM 月 dd 日");// 格式化当天的日期

					String num = Integer.toString(new Random().nextInt(101));
					MiraiBot.getBot(qqbot).getGroup(qqgroup)
							.sendMessage(getsucceedmes.replaceAll("%sendername%", e.getSenderName()).replaceAll("%rpnum%", num));// 发送消息
					String[] temp = {f.format(now),num};
					Time.put(e.getSenderID(),temp);// 放置到hashmap中
				} else {// 如果hashmap中存在
					Date now = new Date();
					SimpleDateFormat f = new SimpleDateFormat("yyyy 年 MM 月 dd 日");
					if (!f.format(now).equals(Time.get(e.getSenderID())[0])) {// 如果不是当天再次发送
						String num = Integer.toString(new Random().nextInt(101));
						MiraiBot.getBot(qqbot).getGroup(qqgroup)
								.sendMessage(getsucceedmes.replaceAll("%sendername%", e.getSenderName()).replaceAll("%rpnum%", num));// 发送消息
						String[] temp = {f.format(now),num};
						Time.put(e.getSenderID(),temp);// 重置时间
					} else {// 如果是当天再次发送
						MiraiBot.getBot(qqbot).getGroup(qqgroup).sendMessage(getfailmes.replaceAll("%sendername%", e.getSenderName()).replaceAll("%rpnum%", Time.get(e.getSenderID())[1]));// 发送还在冷却中消息
					}
				}
			}
			if (e.getMessage().equals(jrrpclear)) {
				if (haspermission(e.getSenderID())) {
					Time.clear();
					MiraiBot.getBot(qqbot).getGroup(qqgroup).sendMessage("HashMap Cleared");
				} else {
					MiraiBot.getBot(qqbot).getGroup(qqgroup).sendMessage("你没有权限");
				}
			}
			if (e.getMessage().equals(sendmap)) {
				if (haspermission(e.getSenderID())) {
					String result = "{";
					for (Map.Entry<Long, String[]> entry : Time.entrySet()) {
			            result += (entry.getKey()+"=["+entry.getValue()[0]+","+entry.getValue()[1]+"]");
			        }
					result += "}";
					MiraiBot.getBot(qqbot).getGroup(qqgroup).sendMessage(result);
				} else {
					MiraiBot.getBot(qqbot).getGroup(qqgroup).sendMessage("你没有权限");
				}
			}
		}

	}

	public class jrrpcom implements CommandExecutor {

		@Override
		@ParametersAreNonnullByDefault
		public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
			if (args.length == 1 && args[0].equals("help")) {
				commandSender.sendMessage("§a--------------[ jrrp ]--------------");
				commandSender.sendMessage("§a/jrrp help              显示本帮助信息");
				commandSender.sendMessage("§a/jrrp reload                 重载配置");
				commandSender.sendMessage("§a/jrrp addadmin <qqid>          加管理");
				commandSender.sendMessage("§a/jrrp deladmin <qqid>          减管理");
				commandSender.sendMessage("§a/jrrp isadmin <qqid>    判断是否是管理");
				commandSender.sendMessage("§a----------[ By LiChris93 ]-----------");
				return true;
			} else if (args.length == 2 && args[0].equals("addadmin")) {
				if (args[1].matches("[1-9][0-9]{4,14}")) {
					list.add(args[1]);
					final boolean sta = null != list && list.size() > 0;
					List<String> templist = new ArrayList<String>();
					if (sta) {
						Set<String> set = new HashSet<String>();
						set.addAll(list);
						templist.addAll(set);
					}
					String temp = "";
					if (templist.size() > 1) {
						for (int i = 0; i < templist.size(); i++) {
							temp += templist.get(i) + ",";
						}
					} else {
						temp = templist.get(0);
					}
					config.set("admin", temp);
					saveConfig();
					commandSender.sendMessage("§a添加完成!" + args[1]);
					return true;
				} else {
					commandSender.sendMessage("§c不正确的QQ号！");
					return true;
				}
			} else if (args.length == 2 && args[0].equals("deladmin")) {
				if (haspermission(Long.valueOf(args[1]))) {
					list.remove(args[1]);
					final boolean sta = null != list && list.size() > 0;
					List<String> templist = new ArrayList<String>();
					if (sta) {
						Set<String> set = new HashSet<String>();
						set.addAll(list);
						templist.addAll(set);
					}
					String temp = "";
					if (templist.size() > 1) {
						for (int i = 0; i < templist.size(); i++) {
							temp += templist.get(i) + ",";
						}
					} else {
						temp = templist.get(0);
					}
					config.set("admin", temp);
					saveConfig();
					commandSender.sendMessage("§a移除完成!" + args[1]);
					return true;
				} else if (!args[1].matches("[1-9][0-9]{4,14}")) {
					commandSender.sendMessage("§c不正确的QQ号！");
					return true;
				} else {
					commandSender.sendMessage("§c该用户不是管理！");
					return true;
				}
			} else if (args.length == 1 && args[0].equals("reload")) {
				try {
					list.clear();
					qqbot = config.getLong("bot");
					qqgroup = config.getLong("group");
					admin = config.getString("admin");
					version = config.getString("version");
					if (admin.contains(",")) {
						String[] temp = admin.split(",");
						for (String i : temp) {
							if (i.matches("[1-9][0-9]{4,14}")) {
								list.add(i);
							} else {
								getLogger().warning(i+"不是有效的qq号");
							}
						}
					} else {
						if (admin.matches("[1-9][0-9]{4,14}")) {
							list.add(admin);
						} else {
							getLogger().warning(admin+"不是有效的qq号");
						}
					}
					commandSender.sendMessage("§aconfig重载完成");
					return true;
				} catch (Exception e) {
					getLogger().info(e.toString());

					commandSender.sendMessage("§cconfig重载失败，详细信息查看控制台");
					return true;
				}
			} else if (args.length == 2 && args[0].equals("isadmin")) {
				if (haspermission(Long.valueOf(args[1]))) {
					commandSender.sendMessage("§a该用户是管理");
				} else {
					commandSender.sendMessage("§c该用户不是管理");
				}
				return true;
			} else {
				commandSender.sendMessage("§ajrrp v" + version + "正在这个服务器上运行, 使用 /jrrp help 来获取帮助");
				return true;
			}

		}
	}

	public boolean haspermission(long qqnum) {
		Iterator<String> it = list.iterator();
		Long tlong = qqnum;
		while (it.hasNext()) {
			if (tlong.toString().equals(it.next())) {
				return true;
			}
		}
		return false;

	}

	public static void main(String[] args) {

//		HashMap<Long, Long> Time = new HashMap<Long, Long>();
//		Time.put(4L, 1L);
//		Time.put(4L, 2L);
//		System.out.println(Time.get(1L).getClass());
//		List<String> templist = new ArrayList<String>();
//		templist.add("test");
//		System.out.println(templist.get(0));
	}
}

package cn.dubidubi.util.quartz;

public class Test {
	// public static void main(String[] args) {
	// Quartz.addJob("lzj", "lzjgroup", "lzj", "lzj", Job.class, "0/5 * * * *
	// ?", "hh", "hhhhh");
	// Quartz.addJob("lzj2", "lzjgroup2", "lzj2", "lzj2", Job.class, "0/5 * * *
	// * ?");
	// System.out.println("main");
	// try {
	// Thread.sleep(7000);
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	// System.out.println("主线程睡醒了");
	// Quartz.updateJobTime("lzj", "lzjgroup", "lzj", "lzj", "0/30 * * * * ?");
	//
	// }

	// 测试极多任务并发
	public static void main(String[] args) {
		int flag = 1;
		while (true) {
			flag++;
			Quartz.addJob("lzj" + flag, "job" + flag, "trigger" + flag, "triggergroup" + flag, Job.class,
					"0/5 * * * * ?");
		}
	}
}

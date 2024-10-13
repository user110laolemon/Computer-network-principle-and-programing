package HW5.hw08;

import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class TicketsSell {

    private static final int WINDOWSNUM = 3;
    private static int totalTickets = 100;
    private static Semaphore semaSale = new Semaphore(1);
    private static Semaphore semaRefund = new Semaphore(1);

    private static TicketSell[] sellers = new TicketSell[WINDOWSNUM];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("欢迎使用票务系统！请输入指令：<窗口> <指令> <票数>，或输入 '退出' 结束程序。");
        System.out.println("窗口范围：1-" + WINDOWSNUM);
        System.out.println("指令：'购票' 或 '退票'");
        System.out.println("票数范围：1-20");

        for (int i = 0; i < WINDOWSNUM; i++) {
            sellers[i] = new TicketSell(i + 1);
            new Thread(sellers[i]).start();
        }

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(100000);
                    addTicket((int) (Math.random() * 5) + 1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("退出")) {
                System.out.println("谢谢使用，再见！");
                break;
            }

            String[] inputs = input.split("\\s+");
            if (inputs.length != 3) {
                System.out.println("无效指令格式，请重新输入。");
                continue;
            }

            try {
                int windowNumber = Integer.parseInt(inputs[0]);
                String action = inputs[1];
                int numTickets = Integer.parseInt(inputs[2]);

                if (windowNumber < 1 || windowNumber > WINDOWSNUM) {
                    System.out.println("窗口号无效，请输入范围内的窗口号。");
                    continue;
                }

                if (!action.equalsIgnoreCase("购票") && !action.equalsIgnoreCase("退票")) {
                    System.out.println("无效指令，请输入 '购票' 或 '退票'。");
                    continue;
                }

                if (numTickets < 1 || numTickets > 20) {
                    System.out.println("票数无效，请输入范围内的票数。");
                    continue;
                }

                TicketSell[] sellers = new TicketSell[WINDOWSNUM];
                for (int i = 0; i < WINDOWSNUM; i++) {
                    sellers[i] = new TicketSell(i + 1);
                    new Thread(sellers[i]).start();
                }

                if (action.equalsIgnoreCase("购票")) {
                    sellers[windowNumber - 1].sellTicket(numTickets); // Sell tickets
                } else {
                    sellers[windowNumber - 1].refundTicket(numTickets); // Refund tickets
                }
            } catch (NumberFormatException e) {
                System.out.println("输入格式错误，请输入整数。");
            }
        }

        scanner.close();
    }

    private static void addTicket(int n) {
        totalTickets += n;
        System.out.println("有新进票数：" + n + "票。到达售票处");

    }

    static class TicketSell implements Runnable {
        private int winNum;

        public TicketSell(int n) {
            this.winNum = n;
        }

        public void run() {
            Thread.currentThread().setName("售票窗口-" + winNum);
            //System.out.println(Thread.currentThread().getName() + "开始工作");

            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void sellTicket(int n) {
            try {
                semaSale.acquire();
                if (totalTickets >= n) {
                    totalTickets -= n;
                    System.out.println("窗口" + this.winNum + "接受购票订单：出售" + n + "票。剩余可购总票数：" + totalTickets);
                } else {
                    System.out.println("现无可购余票，请耐心等待新票、退票或者遗憾离开。");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaSale.release();
            }
        }

        private void refundTicket(int n) {
            try {
                semaRefund.acquire();
                totalTickets += n;
                System.out.println("窗口" + this.winNum + "接受退票订单：退回 " + n + "票。剩余可购总票数：" + totalTickets);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaRefund.release();
            }
        }
    }
}

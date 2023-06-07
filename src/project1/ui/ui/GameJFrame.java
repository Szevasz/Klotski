package project1.ui.ui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GameJFrame extends JFrame implements KeyListener, ActionListener {
    //JFrame 界面，窗体
    //规定:GameJFrame就是游戏的主界面
    //跟游戏相关的所有逻辑写在当前类

    //创建一个二维数组
    //目的：用来管理数据
    int[][] data = new int[4][4];
    public GameJFrame(){
        //初始化界面

        initJFrame();

        //初始化菜单
        initJMenuBar();

        //初始化数据
        initData();

        //初始化图片
        initImage();

        //界面显示，建议写在最后
        this.setVisible(true);
    }

    //记录空白方块在二维数组中的位置
    int x = 0;
    int y = 0;

    //记录当前展示图片路径
    String path = "/Users/szevasz/Desktop/IDEA/puzzle/image/girl/girl1/";

    //定义一个二维数组，存储正确的数组
    int[][] win = {
            {1,2,3,4},
            {5,6,7,8},
            {9,10,11,12},
            {13,14,15,0}
    };

    //记步
    int step = 0;

    //创建选项下面的条目
    JMenuItem replayItem = new JMenuItem("重新游戏");
    JMenuItem reLoginItem = new JMenuItem("重新登录");
    JMenuItem closeItem = new JMenuItem("关闭游戏");

    JMenuItem accountItem = new JMenuItem("公众号");

    private void initData(){
        int[] tempArray = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
        Random r = new Random();
        for(int i = 0;i < tempArray.length;i++){
            //获取到随机索引
            int index = r.nextInt(tempArray.length);
            //交换
            int temp = tempArray[i];
            tempArray[i] = tempArray[index];
            tempArray[index] = temp;
        }
        //遍历一维数组，添加到二维数组
        for(int i = 0;i < tempArray.length;i++){
            if(tempArray[i] == 0){
                x = i / 4;
                y = i % 4;
            }
            data[i / 4][i % 4] = tempArray[i];
        }
    }
    private void initImage() {
        //清空原图片
        this.getContentPane().removeAll();

        if(victory()){
            //显示胜利图标
            JLabel winJLabel = new JLabel(new ImageIcon("/Users/szevasz/Desktop/IDEA/puzzle/image/win.png"));
            winJLabel.setBounds(203,283,197,93);
            this.getContentPane().add(winJLabel);
        }

        JLabel stepCount = new JLabel("步数:" + step);
        stepCount.setBounds(50,30,100,20);
        this.getContentPane().add(stepCount);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                //创建一个JLabel对象
                int number = data[i][j];
                JLabel jLabel = new JLabel(new ImageIcon(path + number + ".jpg"));
                //指定图片位置
                jLabel.setBounds(105 * j + 83,105 * i + 134,105,105);
                //给图片添加边框
                jLabel.setBorder(new BevelBorder(1));
                //把管理容器添加到界面当中
                this.getContentPane().add(jLabel);
            }
        }

        //添加背景图片
        ImageIcon bg = new ImageIcon("/Users/szevasz/Desktop/IDEA/project-test/image/background.png");
        JLabel background = new JLabel(bg);
        background.setBounds(40,40,508,560);
        this.getContentPane().add(background);

        //刷新界面
        this.getContentPane().repaint();
    }

    private void initJMenuBar() {
        //设置整个菜单对象
        JMenuBar jMenuBar = new JMenuBar();

        //创建菜单上面两个选项的对象
        JMenu functionJMenu = new JMenu("功能");
        JMenu aboutJMenu = new JMenu("关于我们");

        //将每一个Item加入道Menu中
        functionJMenu.add(replayItem);
        functionJMenu.add(reLoginItem);
        functionJMenu.add(closeItem);

        aboutJMenu.add(accountItem);

        //给条目绑定事件
        replayItem.addActionListener(this);
        reLoginItem.addActionListener(this);
        closeItem.addActionListener(this);
        accountItem.addActionListener(this);


        //将Menu加入到Menubar
        jMenuBar.add(functionJMenu);
        jMenuBar.add(aboutJMenu);

        //给整个界面设置菜单
        this.setJMenuBar(jMenuBar);
    }

    private void initJFrame() {
        //设置界面宽高
        this.setSize(603,680);
        //设置标题
        this.setTitle("拼图游戏单机版 v1.0");
        //设置界面设置
        this.setAlwaysOnTop(true);
        //设置界面居中
        this.setLocationRelativeTo(null);
        //设置关闭模式
        this.setDefaultCloseOperation(3);
        //取消默认居中模式，可以以XY设置
        this.setLayout(null);
        //给整个界面添加键盘监听
        this.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //按下不送时调用这个方法
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == 65){
            this.getContentPane().removeAll();
            JLabel all = new JLabel(new ImageIcon(path + "all.jpg"));
            all.setBounds(83,134,420,420);
            this.getContentPane().add(all);
            //添加背景图片
            ImageIcon bg = new ImageIcon("/Users/szevasz/Desktop/IDEA/puzzle/image/background.png");
            JLabel background = new JLabel(bg);
            background.setBounds(40,40,508,560);
            this.getContentPane().add(background);
            //刷新界面
            this.getContentPane().repaint();
        }
    }
    
    
    //按下松开时调用
    @Override
    public void keyReleased(KeyEvent e) {
        //判断游戏是否胜利，如果胜利就无法执行下面移动代码
        if(victory()){
            return;
        }
        //对上下左右进行判断
        //左：37 上：38 右：39 下：40
        int code = e.getKeyCode();
        if(code == 37) {
            if(y == 3){
                return;
            }
            data[x][y] = data[x][y+1];
            data[x][y+1] = 0;
            y++;
            step++;
            //重新加载图片
            initImage();
        } else if (code == 38) {
            if(x == 3){
                return;
            }
            data[x][y] = data[x+1][y];
            data[x+1][y] = 0;
            x++;
            step++;
            //重新加载图片
            initImage();
        } else if (code == 39) {
            if(y == 0){
                return;
            }
            data[x][y] = data[x][y-1];
            data[x][y-1] = 0;
            y--;
            step++;
            //重新加载图片
            initImage();
        } else if (code == 40) {
            if(x == 0){
                return;
            }
            data[x][y] = data[x-1][y];
            data[x-1][y] = 0;
            x--;
            step++;
            //重新加载图片
            initImage();
        } else if (code == 65) {
            initImage();
        } else if (code == 87) {
            data = new int[][]{
                {1,2,3,4},
                {5,6,7,8},
                {9,10,11,12},
                {13,14,15,0}
            };
            x = y = 3;
            initImage();
        }
    }


    //判断data中数据和win中的数据是否相同
    public boolean victory(){
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if(data[i][j] != win[i][j])
                    return false;
            }
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if(obj == replayItem){
            //计数器清零
            step = 0;
            //重新打乱二维数组
            initData();
            //重新加载图片
            initImage();

        } else if (obj == reLoginItem) {
            //关闭但前页面
            this.setVisible(false);
            //打开登陆页面
            new LoginJFrame();
        } else if (obj == closeItem) {
            System.exit(0);
        } else if (obj == accountItem) {
            //创建一个弹框对象
            JDialog jDialog = new JDialog();
            JLabel jLabel = new JLabel(new ImageIcon("/Users/szevasz/Desktop/IDEA/puzzle/image/WechatIMG239.jpeg"));
            jLabel.setBounds(0,0,764,1018);
            jDialog. getContentPane().add(jLabel);
            jDialog.setSize(800,1100);
            jDialog.setAlwaysOnTop(true);
            jDialog.setLocationRelativeTo(null);
            jDialog.setModal(true);
            jDialog.setVisible(true);
        }
    }
}
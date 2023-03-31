import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.security.cert.PolicyNode;
import java.util.Locale;
import java.util.Random;

public class SFrame extends JFrame {

    public JTextField minValueText,maxValueText;
    public String title = "随机数生成器";
    public int width = 800;
    public int height = 584;
    public String pictureFileURL = "/picture/";
    public String backgroundIconName = "backgroundIcon.jpg";
    public String imgIconName = "imgIcon.jpg";
    public int value = 0;
    //启动窗口
    public void launchFrame() {
        //窗口标题
        setTitle(title);
        //窗口大小
        setSize(width, height);
        //窗口位置居中
        setLocationRelativeTo(null);
        //不可拖拽大小
        setResizable(false);
        //设置icon图片
        setIconImage(new ImageIcon(SFrame.class.getResource(pictureFileURL + imgIconName)).getImage());
        //获取背景图片并设置为一个标签
        URL backgroundIconURL = SFrame.class.getResource(pictureFileURL + backgroundIconName);
        ImageIcon backgroundIcon = new ImageIcon(backgroundIconURL);
        JLabel backgroundIconLable = new JLabel(backgroundIcon);
        backgroundIconLable.setBounds(0,0,backgroundIcon.getIconWidth(),backgroundIcon.getIconHeight());
        //将背景标签设置到LayeredPane(ContentPane下面)
        getLayeredPane().add(backgroundIconLable,new Integer(Integer.MIN_VALUE));
        //将ContentPane下面设置为透明
        Container container = getContentPane();
        ((JPanel)container).setOpaque(false);
        //设置布局管理器为弹簧布局
        SpringLayout springLayout = new SpringLayout();
        container.setLayout(springLayout);
        //添加标题名称
        JLabel northLable = new JLabel(title);
        //生成组件的约束
        SpringLayout.Constraints northLableConstraints = springLayout.getConstraints(northLable);
        //设置位置
        northLableConstraints.setX(Spring.constant(320));
        northLableConstraints.setY(Spring.constant(200));
        //设置大小
        northLable.setPreferredSize(new Dimension(width,40));
        //设置字体
        northLable.setFont(new Font("宋体", Font.PLAIN, 33));
        container.add(northLable);

        //添加标题名称
        JLabel minValueLable = new JLabel("最小值:");
        //设置大小
        minValueLable.setPreferredSize(new Dimension(width,20));
        //设置字体
        minValueLable.setFont(new Font("宋体", Font.PLAIN, 18));
        //生成组件的约束
        SpringLayout.Constraints minValueLableConstraints = springLayout.getConstraints(minValueLable);
        minValueLableConstraints.setConstraint(SpringLayout.NORTH,Spring.sum(northLableConstraints.getConstraint(SpringLayout.SOUTH),Spring.constant(20)));
        minValueLableConstraints.setConstraint(SpringLayout.WEST,Spring.sum(northLableConstraints.getConstraint(SpringLayout.WEST),Spring.constant(-15)));
        container.add(minValueLable);

        //添加标题名称
        minValueText = new JTextField("");
        //设置大小
        minValueText.setPreferredSize(new Dimension(150,25));
        //设置字体
        minValueText.setFont(new Font("宋体", Font.PLAIN, 18));
        //生成组件的约束
        SpringLayout.Constraints minValueTextConstraints = springLayout.getConstraints(minValueText);
        minValueTextConstraints.setConstraint(SpringLayout.NORTH,minValueLableConstraints.getConstraint(SpringLayout.NORTH));
        minValueTextConstraints.setConstraint(SpringLayout.WEST,Spring.sum(minValueLableConstraints.getConstraint(SpringLayout.WEST),Spring.constant(70)));
        container.add(minValueText);

        //添加标题名称
        JLabel maxValueLable = new JLabel("最大值:");
        //设置大小
        maxValueLable.setPreferredSize(new Dimension(width,20));
        //设置字体
        maxValueLable.setFont(new Font("宋体", Font.PLAIN, 18));
        //生成组件的约束
        SpringLayout.Constraints maxValueLableConstraints = springLayout.getConstraints(maxValueLable);
        maxValueLableConstraints.setConstraint(SpringLayout.NORTH,Spring.sum(minValueLableConstraints.getConstraint(SpringLayout.SOUTH),Spring.constant(20)));
        maxValueLableConstraints.setConstraint(SpringLayout.WEST,minValueLableConstraints.getConstraint(SpringLayout.WEST));
        container.add(maxValueLable);

        //添加标题名称
        maxValueText = new JTextField("");
        //设置大小
        maxValueText.setPreferredSize(new Dimension(150,25));
        //设置字体
        maxValueText.setFont(new Font("宋体", Font.PLAIN, 18));
        //生成组件的约束
        SpringLayout.Constraints maxValueTextConstraints = springLayout.getConstraints(maxValueText);
        maxValueTextConstraints.setConstraint(SpringLayout.NORTH,maxValueLableConstraints.getConstraint(SpringLayout.NORTH));
        maxValueTextConstraints.setConstraint(SpringLayout.WEST,Spring.sum(maxValueLableConstraints.getConstraint(SpringLayout.WEST),Spring.constant(70)));
        container.add(maxValueText);

        //添加标题名称
        JButton jButton = new JButton("开始生成！");
        //设置大小
        jButton.setPreferredSize(new Dimension(200,45));
        //设置字体
        jButton.setFont(new Font("宋体", Font.PLAIN, 24));
        jButton.addActionListener(new MyMonitor());
        //生成组件的约束
        SpringLayout.Constraints jButtonConstraints = springLayout.getConstraints(jButton);
        jButtonConstraints.setConstraint(SpringLayout.NORTH,Spring.sum(northLableConstraints.getConstraint(SpringLayout.NORTH),Spring.constant(160)));
        jButtonConstraints.setConstraint(SpringLayout.WEST,Spring.sum(northLableConstraints.getConstraint(SpringLayout.WEST),Spring.constant(2)));
        container.add(jButton);

        //添加窗口关闭事件
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //窗口显示
        setVisible(true);
    }

    //生成随机数按钮点击事件
    private class MyMonitor implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Random random = new Random();
            int t = random.nextInt(48)+1;
            for(int i = 1 ; i <= t ; i++ ){
                createLable();
            }
        }

        private void createLable(){
            String dataValue = "";
            Random random = new Random();
            try {
                int minValue = Integer.valueOf(minValueText.getText());
                int maxValue = Integer.valueOf(maxValueText.getText());

                int data = maxValue - minValue ;
                if(data <= 0){
                    throw new Exception();
                }
                int range = random.nextInt(data + 1) + minValue ;
                dataValue = String.valueOf(range);
            }catch (Exception ex){
                dataValue = "输入合理整数！";
            }
            SFrame sFrame = new SFrame();
            int dataHeight = 50;
            int dataWidth = 220;
            int dataX = random.nextInt(sFrame.width-20);
            int dataY = random.nextInt(sFrame.height-dataHeight);
            int dataSize = random.nextInt(24)+8;


            int r = random.nextInt(256);
            int g = random.nextInt(256);
            int b = random.nextInt(256);
            Color color = new Color(r, g, b);

            JLabel dataLable = new JLabel(dataValue);
            dataLable.setForeground(color);
            dataLable.setFont(new Font("宋体", Font.PLAIN, dataSize));
            dataLable.setPreferredSize(new Dimension(dataWidth,dataHeight));
            Container contentPane = getContentPane();
            SpringLayout springLayout = (SpringLayout) contentPane.getLayout();
            SpringLayout.Constraints dataLableConstraints = springLayout.getConstraints(dataLable);
            dataLableConstraints.setX(Spring.constant(dataX));
            dataLableConstraints.setY(Spring.constant(dataY));
            contentPane.add(dataLable);
            contentPane.validate();
            contentPane.repaint();
        }
    }
}

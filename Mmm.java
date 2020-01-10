package mmm;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.geom.Point2D;
import java.util.Collections;


public class Mmm extends JFrame{
 
    
   public MyPanel panel; 
   public JButton   przycisk;
   public JTextField textA = new JTextField("1",15);
   public JTextField textFreq = new JTextField("1",15);
   public JTextField textT = new JTextField("1",15);
   public JTextField textkp = new JTextField("1",15);
   public JTextField textTp = new JTextField("1",15);
   public JTextField textB = new JTextField("1",15);
   public JTextField textb = new JTextField("1",15);
   public JTextField textCzas = new JTextField("1",15);
   public JRadioButton rad_harmon = new JRadioButton("harmoniczny");
   public JRadioButton rad_prostok = new JRadioButton("prostokatny");
   public JRadioButton rad_trojkat = new JRadioButton("trojkatny");
   public JComboBox wybor;
   public String wybor_str[];
   public double amplituda;
   public double czestotliwosc;
   public ArrayList<Double> input = new ArrayList();
   public ArrayList<Double> odpowiedz = new ArrayList();
   public ArrayList<Double> pochodna_odpowiedzi = new ArrayList();
   public final double dt = 12d;
   public final double dt1 = 2d;
   public final double delta_t = 0.1d;
   public double kp;
   public double Tp;
   public double T;
   public double b;
   public double B;
   public double f;
   public int typ_inputu;
   public double Czas;
   public double max;
   
    public ArrayList<Double> input_1 = new ArrayList();
   public ArrayList<Double> odpowiedz_1 = new ArrayList();

   private class ObslugaPrzycisku implements ActionListener{

       private JFrame ref_okno;

       ObslugaPrzycisku(JFrame okno){
            ref_okno = okno;
       }

       public void actionPerformed(ActionEvent e) {
            JButton bt = (JButton)e.getSource();
            if(bt==przycisk)
            Set();            
        }

   }

   public Mmm()
   {
        super("Okno interfejsu");
        setSize(1500,1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
        JPanel panel = new MyPanel(this);
        String[] select = {"Draw graph","Erase"};
		
        przycisk = new JButton("SET");
        przycisk.addActionListener(new ObslugaPrzycisku(this));

        
        JPanel panelPrzyciski   = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelPrzyciski.add(przycisk);
        JPanel panelA = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelFreq = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelT = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelkp = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelTp = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelB = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelb = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelCzas = new JPanel(new FlowLayout(FlowLayout.LEFT));
       
        JLabel labelA = new JLabel("Amplituda:");
        JLabel labelFreq = new JLabel("Czestotliwosc:");
        JLabel labelT = new JLabel("T:");
        JLabel labelkp = new JLabel("kp:");
        JLabel labelTp = new JLabel("Tp:");
        JLabel labelB = new JLabel("B:");
        JLabel labelb = new JLabel("b:");
        JLabel labelCzas = new JLabel("Czas:");
        
        panelA.add(labelA,BorderLayout.WEST);
        panelA.add(textA,BorderLayout.CENTER);
        panelFreq.add(labelFreq,BorderLayout.WEST);
        panelFreq.add(textFreq,BorderLayout.CENTER);
        panelT.add(labelT,BorderLayout.WEST);
        panelT.add(textT,BorderLayout.CENTER);
        panelkp.add(labelkp,BorderLayout.WEST);
        panelkp.add(textkp,BorderLayout.CENTER);
        panelTp.add(labelTp,BorderLayout.WEST);
        panelTp.add(textTp,BorderLayout.CENTER);
        panelB.add(labelB,BorderLayout.WEST);
        panelB.add(textB,BorderLayout.CENTER);
        panelb.add(labelb,BorderLayout.WEST);
        panelb.add(textb,BorderLayout.CENTER);
        panelb.add(labelCzas,BorderLayout.WEST);
        panelb.add(textCzas,BorderLayout.CENTER);
        
        JPanel gornyPasek  = new JPanel(new FlowLayout(FlowLayout.LEFT));
        gornyPasek.add(rad_harmon);
        gornyPasek.add(rad_prostok);
        gornyPasek.add(rad_trojkat);
        
        JPanel panel_lewy = new JPanel();
        panel_lewy.setLayout(new GridLayout(20,1));
        panel_lewy.add(gornyPasek,BorderLayout.WEST);
        panel_lewy.add(panelA);
        panel_lewy.add(panelFreq);
        panel_lewy.add(panelT);
        panel_lewy.add(panelkp);
        panel_lewy.add(panelTp);
        panel_lewy.add(panelB);
        panel_lewy.add(panelb);
        panel_lewy.add(panelCzas);
        panel_lewy.add(panelPrzyciski,BorderLayout.WEST);
        
        add(panel_lewy,BorderLayout.WEST);
        add(panel);
        pack();
        setVisible(true);
   }
      
   
   public void Input_Prostokatny()
    {
     input.clear();
     typ_inputu = 2;
     double probka = 0;
     double delta = Czas/500;
     for(double i=0; i<500; i++)
       {
           double Y = (amplituda*Math.signum(Math.sin(2*Math.PI*czestotliwosc*probka)));
           input.add(Y);
           probka = probka+delta;
       }
     Skalowanie_Sygnalu();
     Odpowiedz();
     Skalowanie_Odpowiedzi();
    }
   
   public void Input_Trojkatny()
    {
     input.clear();
     typ_inputu =3;
     double probka = 0;
      double delta = Czas/500;
     for(double i=0; i<500; i++)
       {
           double Y = (amplituda*(2*Math.PI*Math.asin(Math.sin(2*Math.PI*0.5d*czestotliwosc*probka)))/10);
           input.add(Y);
           probka = probka+delta;
       }
     Skalowanie_Sygnalu();
     Odpowiedz();
     Skalowanie_Odpowiedzi();
     
    }
   
    public void Input_Harmoniczny()
    {
     input.clear();
     typ_inputu = 1;
     
      double probka = 0;
      double delta = Czas/500;
       
     for(double i=0; i<500; i++)
       {
           double Y = (amplituda*Math.sin(2*Math.PI*czestotliwosc*probka));
           
           input.add(Y);
           probka = probka+delta;
           
           
       }
     Skalowanie_Sygnalu();
     Odpowiedz();
     Skalowanie_Odpowiedzi();
     
    }
    
   public void Skalowanie_Sygnalu()
   {       
       
    for(int i=0; i<500; i++)
    {
        Double Y =150-input.get(i)/amplituda*100;
        input_1.add(Y);
        
    }}
   
      public void Skalowanie_Odpowiedzi()
   {       
       
    for(int i=0; i<500; i++)
    {
        Double Y =600-odpowiedz.get(i)/max*200;
        odpowiedz_1.add(Y);
        
    }}
   
   

   public void Odpowiedz()
   {      
       odpowiedz.clear();
       pochodna_odpowiedzi.clear();
       odpowiedz_1.clear();
       odpowiedz.add(0d);
       pochodna_odpowiedzi.add(0d);
       
       if(input.get(0)<-b)
       {
           f=-B;
       }
       else f=B;
       
       for(int i=1; i<500;i++)
       {
           pochodna_odpowiedzi.add(pochodna_odpowiedzi.get(i-1)+delta_t*((-pochodna_odpowiedzi.get(i-1)+kp*f)/Tp));
           
           odpowiedz.add(odpowiedz.get(i-1)+delta_t*pochodna_odpowiedzi.get(i-1));
           
           if(f==B && (input.get(i)-odpowiedz.get(i)-T*pochodna_odpowiedzi.get(i))<=-b)
           {
               f=-B;
           }
           else if(f==-B && (input.get(i)-odpowiedz.get(i)-T*pochodna_odpowiedzi.get(i))>=b)
           {
               f=B;
           }
       }
        max = Collections.max(odpowiedz);
       
       
       this.repaint();
   }
   
   public void Set()
   {
       amplituda = Double.parseDouble(textA.getText());
       czestotliwosc = Double.parseDouble(textFreq.getText());
       T = Double.parseDouble(textT.getText());
       kp = Double.parseDouble(textkp.getText());
       Tp = Double.parseDouble(textTp.getText());
       B = Double.parseDouble(textB.getText());
       b = Double.parseDouble(textb.getText());
       Czas = Double.parseDouble(textCzas.getText());
       input.clear();
       input_1.clear();
       
       if(rad_harmon.isSelected() && !(rad_prostok.isSelected()) && !(rad_trojkat.isSelected()))
           Input_Harmoniczny();
       else if(rad_prostok.isSelected() && !(rad_harmon.isSelected()) && !(rad_trojkat.isSelected()))
           Input_Prostokatny();
       else if(rad_trojkat.isSelected() && !(rad_harmon.isSelected()) && !(rad_prostok.isSelected()))
           Input_Trojkatny();
       else System.out.println("Musi byc zaznaczony jeden rodzaj inputu");
       
       
       this.repaint();
   }
   
   public static void main(String args[]){
       new Mmm();
   }
   
   
}

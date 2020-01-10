package mmm;

import java.awt.*;

import javax.swing.JPanel;


public class MyPanel extends JPanel{
    
    Mmm mmm;
    
    public MyPanel(Mmm mmm)
    {
        this.mmm = mmm;
        setPreferredSize(new Dimension(1200, 950));
    }
    
    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        //mmm.typ_inputu=1;
        
        
                //OSIE
        g2d.drawLine(100,10,100,290);
        g2d.drawLine(100,150,650,150);
        g2d.drawLine(95,15,100,10);
        g2d.drawLine(100,10,105,15);
        g2d.drawLine(645,145,650,150);
        g2d.drawLine(645,155,650,150);
        g2d.drawLine(600,145,600,155);
        g2d.drawLine(95,50,105,50);
        g2d.drawLine(95,250,105,250);
        
        g2d.drawLine(20,600,1100,600);
        g2d.drawLine(20,320,20,880);
        g2d.drawLine(15,325,20,320);
        g2d.drawLine(20,320,25,325);
        g2d.drawLine(1095,595,1100,600);
        g2d.drawLine(1095,605,1100,600);
        g2d.drawLine(15,400,25,400);
        
        if(!(mmm.input.isEmpty()))
        {   
            
            g2d.drawString(Double.toString(mmm.amplituda),75 ,55);
            g2d.drawString(Double.toString(mmm.amplituda*(-1)),75 ,255 );
            g2d.drawString(Double.toString(mmm.Czas),600 ,162);
            
            g2d.setColor(Color.blue);
            for(int i=1;i<500;i++)
            { 
                g2d.drawLine((int)(100+(i-1)),(int)(1*mmm.input_1.get(i-1)),
                        (int)(100+(i)),(int)(1*mmm.input_1.get(i)));
            }
            

        
        }

        
        //RYSOWANIE ODPOWIEDZI
        if(!(mmm.odpowiedz.isEmpty()))
        {
            g2d.drawString(Double.toString(mmm.max),20 ,400);
        for(int i=0;i<499;i++)
        {
            g2d.drawLine((int)(20+i*mmm.dt1),(int)(1*mmm.odpowiedz_1.get(i)),(int)(20+(i+1)*mmm.dt1),(int)(1*mmm.odpowiedz_1.get(i+1)));
        };
        }
        
        

    }
}

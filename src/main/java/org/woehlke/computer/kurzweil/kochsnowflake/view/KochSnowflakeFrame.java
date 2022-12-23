package org.woehlke.computer.kurzweil.kochsnowflake.view;

import lombok.Getter;
import org.woehlke.computer.kurzweil.kochsnowflake.config.ComputerKurzweilProperties;
import org.woehlke.computer.kurzweil.kochsnowflake.control.ControllerThread;
import org.woehlke.computer.kurzweil.kochsnowflake.model.KochSnowflakeModel;
import org.woehlke.computer.kurzweil.kochsnowflake.model.geometry.LatticePoint;
import org.woehlke.computer.kurzweil.kochsnowflake.model.geometry.LatticeRectangle;
import org.woehlke.computer.kurzweil.kochsnowflake.view.canvas.KochSnowflakeCanvas;
import org.woehlke.computer.kurzweil.kochsnowflake.view.labels.PanelCopyright;
import org.woehlke.computer.kurzweil.kochsnowflake.view.labels.PanelSubtitle;

import javax.accessibility.Accessible;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageObserver;
import java.io.Serial;
import java.io.Serializable;

/**
 * Koch Snowflake. A Fractal with self self-similarity.
 * (C) 2006 - 2022 Thomas Woehlke
 * @author Thomas Woehlke
 *
 * @see ControllerThread
 * @see KochSnowflakeCanvas
 * @see KochSnowflakeModel
 * @see PanelSubtitle
 * @see PanelCopyright
 *
 * @see JFrame
 * @see ImageObserver
 * @see WindowListener
 * @see MouseListener
 *
 * @see <a href="https://github.com/Computer-Kurzweil/kochsnowflake">Github Repository</a>
 * @see <a href="https://java.woehlke.org/kochsnowflake/">Maven Project Reports</a>
 *
 * Date: 04.02.2006
 * Time: 18:47:46
 */
@Getter
public class KochSnowflakeFrame extends JFrame implements ImageObserver,
        MenuContainer,
        Serializable,
        Accessible,
        WindowListener,
        MouseListener {

    @Serial
    private final static long serialVersionUID = 242L;

    private final PanelSubtitle panelSubtitle;
    private final PanelCopyright panelCopyright;

    private volatile ControllerThread controller;
    private volatile KochSnowflakeCanvas canvas;
    private volatile KochSnowflakeModel model;
    private volatile LatticeRectangle rectangleBounds;
    private final ComputerKurzweilProperties config;

    public KochSnowflakeFrame(ComputerKurzweilProperties config) {
        super(config.getKochsnowflake().getView().getTitle());
        this.config = config;
        this.model = new KochSnowflakeModel(this);
        this.canvas = new KochSnowflakeCanvas(this);
        this.controller = new ControllerThread( this);
        this.panelSubtitle = new PanelSubtitle(config.getKochsnowflake().getView().getSubtitle());
        this.panelCopyright = new PanelCopyright(config.getKochsnowflake().getView().getCopyright());
        BoxLayout layout = new BoxLayout(rootPane, BoxLayout.PAGE_AXIS);
        rootPane.setLayout(layout);
        rootPane.add(panelSubtitle);
        rootPane.add(canvas);
        rootPane.add(panelCopyright);
        this.addWindowListener(this);
        this.canvas.addMouseListener(   this);
        this.showMeInit();
        this.setModeSwitch();
    }

    public void windowOpened(WindowEvent e) {
        showMe();
    }

    public void windowClosing(WindowEvent e) {
        this.controller.exit();
    }

    public void windowClosed(WindowEvent e) {
        this.controller.exit();
    }

    public void windowIconified(WindowEvent e) {}

    public void windowDeiconified(WindowEvent e) {
        showMe();
    }

    public void windowActivated(WindowEvent e) {
        showMe();
    }

    public void windowDeactivated(WindowEvent e) {}


    @Override
    public void mouseClicked(MouseEvent e) {
        LatticePoint c = new LatticePoint(e.getX(), e.getY());
        this.model.click(c);
        showMe();
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    /**
     * Things to do, to show the Application Window started by Constructor
     */
    public void showMeInit() {
        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = this.model.getWorldDimensions().getWidth();
        //double width = this.model.getWorldDimensions().getWidth() + 16;
        double height  = this.model.getWorldDimensions().getHeight() + 120;
        double startX = (screenSize.getWidth() - width) / 2d;
        double startY = (screenSize.getHeight() - height) / 2d;
        int myheight = Double.valueOf(height).intValue();
        int mywidth = Double.valueOf(width).intValue();
        int mystartX = Double.valueOf(startX).intValue();
        int mystartY = Double.valueOf(startY).intValue();
        this.rectangleBounds = LatticeRectangle.of(mystartX, mystartY, mywidth, myheight);
        this.setBounds(
            this.rectangleBounds.getStart().getX(),
            this.rectangleBounds.getStart().getY(),
            this.rectangleBounds.getDimension().getWidth(),
            this.rectangleBounds.getDimension().getHeight()
        );
        this.setSize(
            this.rectangleBounds.getDimension().getWidth(),
            this.rectangleBounds.getDimension().getHeight()
        );
        this.setPreferredSize(
            new Dimension(
                this.rectangleBounds.getDimension().getWidth(),
                this.rectangleBounds.getDimension().getHeight()
            )
        );
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        toFront();
    }

    /**
     * Things to do, to show the Application Window again.
     */
    public void showMe() {
        this.pack();
        this.setBounds(
            this.rectangleBounds.getStart().getX(),
            this.rectangleBounds.getStart().getY(),
            this.rectangleBounds.getDimension().getWidth(),
            this.rectangleBounds.getDimension().getHeight()
        );
        this.setSize(
            this.rectangleBounds.getDimension().getWidth(),
            this.rectangleBounds.getDimension().getHeight()
        );
        this.setPreferredSize(
            new Dimension(
                this.rectangleBounds.getDimension().getWidth(),
                this.rectangleBounds.getDimension().getHeight()
            )
        );
        this.setVisible(true);
        this.toFront();
    }

    public void setModeSwitch() {
        canvas.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
    }

    public KochSnowflakeCanvas getCanvas() {
        return canvas;
    }

    public void start() {
        this.model.start();
        this.controller.start();
        showMe();
    }
}

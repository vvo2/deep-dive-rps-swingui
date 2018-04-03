package edu.cnm.deepdive.rps.controller;

import edu.cnm.deepdive.rps.model.Terrain;
import edu.cnm.deepdive.rps.view.Surface;
import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class Player {

  private static final String WINDOW_TITLE = "Deep Dive: Rock-Paper-Scissor Ecosystem";
  private static final String SETUP_BUTTON = "Reset";
  private static final String RUN_BUTTON_UNSELECTED = "Run!";
  private static final String RUN_BUTTON_SELECTED = "Stop";

  private static final char SETUP_MNEMONIC = 'T';
  private static final char RUN_MNEMONIC_UNSELECTED = 'R';
  private static final char RUN_MNEMONIC_SELECTED = 'S';

  private static final float SCALE = 15f;
  private static final int PADDING = 15;
  private static final int MAX_SPEED = 25;
  private static final int MIN_SPEED = 1;
  private static final int DEFAULT_SPEED = 13;
  private static final int SPEED_SCALE = 5;
  private static final int WAIT_INTERVAL = 1;

  private Terrain terrain;
  private Surface surface;
  private JButton setup;
  private JSlider speedSlider;
  private JToggleButton run;

  private boolean uiSetup;
  private boolean running;
  private long lastUpdate;

  public void init() {
    terrain = new Terrain();
    terrain.reset();
    uiSetup = false;
    running = false;
    SwingUtilities.invokeLater(this::buildGui);
    synchronized (this) {
      while (!uiSetup) {
        try {
          wait();
        } catch (InterruptedException ex) {
          // Do nothing.
        }
      }
    }
  }

  private void buildGui() {
    JFrame frame = new JFrame(WINDOW_TITLE);
    frame.setLayout(new BorderLayout());
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    surface = new Surface(terrain, SCALE);
    frame.add(surface, BorderLayout.NORTH); //boarder set prior to this

    JPanel controls = new JPanel(new GridLayout(1, 3)); //must define row and cols
    controls.setBorder(new EmptyBorder(PADDING, PADDING, PADDING, PADDING));

    setup = new JButton(SETUP_BUTTON);
    setup.setMnemonic(SETUP_MNEMONIC);
    setup.addActionListener(event -> setup());

    speedSlider = new JSlider(MIN_SPEED, MAX_SPEED, DEFAULT_SPEED);

    run = new JToggleButton(RUN_BUTTON_UNSELECTED);
    run.setMnemonic(RUN_MNEMONIC_UNSELECTED);
    run.addActionListener(event -> run());

    controls.add(setup);
    controls.add(speedSlider);
    controls.add(run);

    frame.add(controls, BorderLayout.SOUTH);
    frame.pack();
    frame.setVisible(true);

    surface.repaint();

    synchronized (this) {
      uiSetup = true;
      notify();
    }
  }

  private void setup() {
    setup.setEnabled(false);
    run.setEnabled(false);
    synchronized (this) { // "this" is the current talking stick
      terrain.reset();
      notify();
    }
    run.setEnabled(true);
    setup.setEnabled(true);
  }

  private void run() {
    if (run.isSelected()) {
      setup.setEnabled(false);
      run.setText(RUN_BUTTON_SELECTED);
      run.setMnemonic(RUN_MNEMONIC_SELECTED);
      synchronized (this) {
        running = true;
        notify();
      }
    } else {
      synchronized (this) {
        running = false;
        notify();
      }
      run.setText(RUN_BUTTON_UNSELECTED);
      run.setMnemonic(RUN_MNEMONIC_UNSELECTED);
      setup.setEnabled(true);
    }
  }

  public synchronized void start() {
    while (true) {
      if (running) {
        for (int i = 0; i < speedSlider.getValue() * SPEED_SCALE; i++) {
          terrain.step();
        }
        updateView();
        try {
          wait(WAIT_INTERVAL);
        } catch (InterruptedException ex) {
          // Do nothing.
        }
      } else if (lastUpdate != terrain.getSteps()) {
        updateView();
      } else {
        try {
          wait();
        } catch (InterruptedException ex) {
          // Do nothing.
        }
      }
    }
  }

  private void updateView() {
    lastUpdate = terrain.getSteps();
    SwingUtilities.invokeLater(() -> surface.repaint());
  }
}

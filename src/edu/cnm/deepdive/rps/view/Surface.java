package edu.cnm.deepdive.rps.view;

import edu.cnm.deepdive.rps.model.Breed;
import edu.cnm.deepdive.rps.model.Terrain;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class Surface extends JPanel {

  private static final float SATURATION = 1f;
  private static final float BRIGHTNESS = 1f;
  private static final int INSET = 1;
  private static final Color BACKGROUND_COLOR = Color.BLACK;

  private final Breed[][] population;
  private final int size;
  private final float scale;
  private final Color[] colors;

  public Surface(Terrain terrain, float scale) {
    super(true);
    population = terrain.getPopulation();
    size = population.length;
    this.scale = scale;
    colors = new Color[Breed.values().length];
    float hue = 0;
    float hueInterval = 1f / colors.length;  //hue for Color is range from 0 to 1 (red back to red)
    for (int i = 0; i < colors.length; i++) {
      colors[i] = new Color(Color.HSBtoRGB(hue, SATURATION, BRIGHTNESS));
      hue += hueInterval;
    }
    setBorder(LineBorder.createBlackLineBorder());
    setBackground(BACKGROUND_COLOR);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    for (int i = 0; i < population.length; i++) {
      int verticalOffset = Math.round(i * scale);
      int top = INSET + verticalOffset;
      int height = Math.round((i + 1) * scale) - verticalOffset;
      for (int j = 0; j < population[i].length; j++) {
        int horizontalOffset = Math.round(j * scale);
        int left = INSET + horizontalOffset;
        int width = Math.round((j + 1) * scale) - horizontalOffset;
        Breed cell = population[i][j];
        g.setColor(colors[cell.ordinal()]);
        g.fillOval(left, top, width, height);

      }
    }
  }

  @Override
  public Dimension getPreferredSize() {
    int pixelSize = 1 + 2 * INSET + Math.round(size * scale);
    return new Dimension(pixelSize, pixelSize);
  }

}

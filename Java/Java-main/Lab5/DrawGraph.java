package Lab5;
import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class DrawGraph extends JPanel {
    private Integer padding = 50;
    private Integer labelPadding = 10;
    private Integer pointWidth = 4;
    private Integer numberYDivisions = 10;

    private Color lineColor = new Color(44, 102, 230, 180);
    private Color pointColor = new Color(100, 100, 100, 180);
    private Color gridColor = new Color(200, 200, 200, 200);

    private String title;

    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);

    private List<Double> scores;

    public DrawGraph(List<Double> scores, String title) {
        this.scores = scores;
        this.title = title;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Double xScale = ((double) getWidth() - (2 * padding) - labelPadding) / (scores.size() - 1);
        Double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (getMaxScore() - getMinScore());
        List<Point> graphPoints = new ArrayList<>();
        
        for (int i = 0; i < scores.size(); i++) {
            Integer x1 = (int) (i * xScale + padding + labelPadding);
            Integer y1 = (int) ((getMaxScore() - scores.get(i)) * yScale + padding);
            graphPoints.add(new Point(x1, y1));
        }

        g2.setColor(Color.WHITE);
        g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding,
                getHeight() - 2 * padding - labelPadding);
        g2.setColor(Color.BLACK);
        g2.drawString(this.title, (getWidth() / 2 - padding - labelPadding) , 20);
        for (int i = 0; i < numberYDivisions + 1; i++) {
            Integer x0 = padding + labelPadding;
            Integer x1 = pointWidth + padding + labelPadding;
            Integer y0 = getHeight()
                    - ((i * (getHeight() - padding * 2 - labelPadding)) / numberYDivisions + padding + labelPadding);
            Integer y1 = y0;
            if (scores.size() > 0) {
                g2.setColor(gridColor);
                g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
                g2.setColor(Color.BLACK);
                String yLabel = ((int) ((getMinScore()
                        + (getMaxScore() - getMinScore()) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }

        int[] x4 = { 10, 100, 1000, 10_000, 100_000 };
        for (int i = 0; i < scores.size(); i++) {
            if (scores.size() > 1) {
                Integer x0 = i * (getWidth() - padding * 2 - labelPadding) / (scores.size() - 1) + padding + labelPadding;
                Integer x1 = x0;
                Integer y0 = getHeight() - padding - labelPadding;
                Integer y1 = y0 - pointWidth;
                if ((i % ((int) ((scores.size() / 20.0)) + 1)) == 0) {
                    g2.setColor(gridColor);
                    g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
                    g2.setColor(Color.BLACK);
                    String xLabel = x4[i] + "";
                    FontMetrics metrics = g2.getFontMetrics();
                    int labelWidth = metrics.stringWidth(xLabel);
                    g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
                }
                g2.drawLine(x0, y0, x1, y1);
            }
        }

        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() - padding,
                getHeight() - padding - labelPadding);

        Stroke oldStroke = g2.getStroke();
        g2.setColor(lineColor);
        g2.setStroke(GRAPH_STROKE);
        for (int i = 0; i < graphPoints.size() - 1; i++) {
            Integer x1 = graphPoints.get(i).x;
            Integer y1 = graphPoints.get(i).y;
            Integer x2 = graphPoints.get(i + 1).x;
            Integer y2 = graphPoints.get(i + 1).y;
            g2.drawLine(x1, y1, x2, y2);
        }

        g2.setStroke(oldStroke);
        g2.setColor(pointColor);
        for (int i = 0; i < graphPoints.size(); i++) {
            Integer x = graphPoints.get(i).x - pointWidth / 2;
            Integer y = graphPoints.get(i).y - pointWidth / 2;
            Integer ovalW = pointWidth;
            Integer ovalH = pointWidth;
            g2.fillOval(x, y, ovalW, ovalH);
        }
    }

    private double getMinScore() {
        double minScore = Double.MAX_VALUE;
        for (Double score : scores) {
            minScore = Math.min(minScore, score);
        }

        return minScore;
    }

    private double getMaxScore() {
        double maxScore = Double.MIN_VALUE;
        for (Double score : scores) {
            maxScore = Math.max(maxScore, score);
        }

        return maxScore;
    }
}

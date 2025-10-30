import java.awt.*;

public class Button {
	private int x, y, width, height;
	private final String text;
	private final Image icon;
	private final Font font = new Font("American Typewriter", Font.BOLD, 24);
	private final Color buttonColor = new Color(0x6BD172);
	private final Color hoverColor = new Color(0x5AC262);
	private final Color textColor = new Color(0xEBFCDC);
	private boolean hover = false;
	private int arc = 30;

	// constructor for text button
	public Button(String text, int x, int y, int width, int height) {
		this.text = text;
		this.icon = null;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	// constructor for icon button
	public Button(Image icon, int x, int y, int width, int height) {
		this.icon = icon;
		this.text = null;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void setHover(boolean hover) {
		this.hover = hover;
	}

	public boolean contains(Point p) {
		return new Rectangle(x, y, width, height).contains(p);
	}

	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		try {
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			// shadow layers
			int layers = 4;
			int shadowOffset = 1;
			int shadowSpread = 1;
			for (int i = 0; i < layers; i++) {
				int offset = shadowOffset + i * shadowSpread;
				int alpha = Math.max(0, 20 - i * 5);
				g2d.setColor(new Color(0, 0, 0, alpha));
				g2d.fillRoundRect(x + offset, y + offset, width, height, arc, arc);
			}

			// button background (hover change)
			g2d.setColor(hover ? hoverColor : buttonColor);
			g2d.fillRoundRect(x, y, width, height, arc, arc);

			// draw icon or text
			if (icon != null) {
				// draw icon centered
				int iconSize = Math.min(width, height) - 20; // padding
				int iconX = x + (width - iconSize) / 2;
				int iconY = y + (height - iconSize) / 2;
				g2d.drawImage(icon, iconX, iconY, iconSize, iconSize, null);
			} else if (text != null) {
				// draw text centered
				g2d.setFont(font);
				FontMetrics fm = g2d.getFontMetrics();
				int textWidth = fm.stringWidth(text);
				int textX = x + (width - textWidth) / 2;
				int textY = y + (height - fm.getHeight()) / 2 + fm.getAscent();

				g2d.setColor(textColor);
				g2d.drawString(text, textX, textY);
			}
		} finally {
			g2d.dispose();
		}
	}
}

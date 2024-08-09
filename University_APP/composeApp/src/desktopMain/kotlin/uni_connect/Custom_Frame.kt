package uni_connect

import java.awt.BorderLayout
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.geom.RoundRectangle2D
import javax.swing.JFrame

class CustomRoundedFrame : JFrame() {
    init {
        // Set the frame to be undecorated
        isUndecorated = true
        // Set the frame shape to be a rounded rectangle
        shape = RoundRectangle2D.Double(0.0, 0.0, 800.0, 600.0, 30.0, 30.0)
        // Set the frame size
        setSize(800, 600)
        // Add a sample content panel
        contentPane.background = Color.WHITE
        contentPane.layout = BorderLayout()
        defaultCloseOperation = EXIT_ON_CLOSE
    }

    override fun paint(g: Graphics) {
        super.paint(g)
        val g2d = g as Graphics2D
        // Apply the rounded rectangle shape
        g2d.clip = shape
        g2d.color = Color.WHITE
        g2d.fill(g2d.clip)
    }
}
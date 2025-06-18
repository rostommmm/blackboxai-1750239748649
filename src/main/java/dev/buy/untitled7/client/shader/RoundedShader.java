package dev.buy.untitled7.client.shader;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderEffect;
import net.minecraft.util.Identifier;
import net.minecraft.client.util.math.MatrixStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RoundedShader {
    private static final Logger LOGGER = LogManager.getLogger();
    private static ShaderEffect roundedShader;
    private static final Identifier SHADER_LOCATION = new Identifier("untitled7", "shaders/program/rounded_corners.json");
    
    public static void initShader() {
        try {
            MinecraftClient client = MinecraftClient.getInstance();
            roundedShader = new ShaderEffect(
                client.getTextureManager(),
                client.getResourceManager(),
                client.getFramebuffer(),
                SHADER_LOCATION
            );
            roundedShader.setupDimensions(client.getWindow().getFramebufferWidth(), client.getWindow().getFramebufferHeight());
            LOGGER.info("Successfully loaded rounded corners shader");
        } catch (Exception e) {
            LOGGER.error("Failed to load rounded corners shader", e);
            roundedShader = null;
        }
    }

    public static void renderRoundedRect(MatrixStack matrices, int x, int y, int width, int height, float radius) {
        if (roundedShader == null) {
            // Fallback to regular rectangle if shader failed to load
            net.minecraft.client.gui.DrawableHelper.fill(matrices, x, y, x + width, y + height, 0xFF1E1E1E);
            return;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        
        // Сохраняем текущее состояние
        RenderSystem.pushMatrix();
        RenderSystem.enableBlend();
        
        // Устанавливаем параметры шейдера
        roundedShader.setUniformValue("OutSize", width, height);
        roundedShader.setUniformValue("Radius", radius);
        
        // Применяем шейдер
        roundedShader.render(client.getTickDelta());
        
        // Восстанавливаем состояние
        RenderSystem.popMatrix();
    }

    public static void resize(int width, int height) {
        if (roundedShader != null) {
            roundedShader.setupDimensions(width, height);
        }
    }

    public static void cleanup() {
        if (roundedShader != null) {
            roundedShader.close();
            roundedShader = null;
        }
    }
}

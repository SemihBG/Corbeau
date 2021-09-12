package com.semihbkgr.corbeau.component;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;


@Component
public class RandomImageGenerator {

    static final int DEFAULT_GENERATION_SIZE = 8;
    static final int DEFAULT_SCALE_BY = 8;
    static final int MIN_DRAWN_SQUARE_COUNT = 4;

    private final int size;
    private final DefaultDataBufferFactory dataBufferFactory;

    public RandomImageGenerator(@Value("${image.random.size:#{null}}") Integer size) {
        if (size != null && size < 0)
            throw new IllegalArgumentException();
        this.size = size != null ? size : DEFAULT_GENERATION_SIZE;
        this.dataBufferFactory = new DefaultDataBufferFactory();
    }

    public Mono<DataBuffer> generate(@NonNull String seed, int scaleBy) {
        if (scaleBy < 1 || seed.length() < 1)
            Mono.error(new IllegalArgumentException());
        return Mono.create(monoSink -> {
            try {
                var r = new Random((long) seed.length() * seed.length() * seed.chars().sum());
                final float hue = r.nextFloat();
                final float saturation = (r.nextInt(2000) + 1000) / 10000f;
                final float luminance = 0.9f;
                final Color color = Color.getHSBColor(hue, saturation, luminance);
                var targetSize = size * scaleBy;
                var bufferedImage = new BufferedImage(size, size, BufferedImage.TYPE_4BYTE_ABGR);
                var drawnSquareCount = 0;
                while (drawnSquareCount < MIN_DRAWN_SQUARE_COUNT)
                    for (var i = 0; i < size / 2; i++)
                        for (var j = 0; j < size; j++)
                            if (r.nextFloat() < .17f) {
                                bufferedImage.setRGB(i, j, color.getRGB());
                                drawnSquareCount++;
                            } else
                                bufferedImage.setRGB(i, j, 0xff000000);
                for (var i = size / 2; i < size; i++)
                    for (var j = 0; j < size; j++)
                        bufferedImage.setRGB(i, j, bufferedImage.getRGB(size - i - 1, j));
                var image = bufferedImage.getScaledInstance(targetSize, targetSize, Image.SCALE_DEFAULT);
                var outputBufferedImage = new BufferedImage(targetSize, targetSize, BufferedImage.TYPE_4BYTE_ABGR);
                outputBufferedImage.getGraphics().drawImage(image, 0, 0, null);
                var byteArrayOutputStream = new ByteArrayOutputStream();
                ImageIO.write(outputBufferedImage, "png", byteArrayOutputStream);
                var dataBuffer = dataBufferFactory.allocateBuffer(targetSize * targetSize * Integer.BYTES);
                dataBuffer.write(byteArrayOutputStream.toByteArray());
                monoSink.success(dataBuffer);
            } catch (IOException e) {
                monoSink.error(e);
            }
        });
    }

    public Mono<DataBuffer> generate(@NonNull String seed) {
        return generate(seed, DEFAULT_SCALE_BY);
    }

}

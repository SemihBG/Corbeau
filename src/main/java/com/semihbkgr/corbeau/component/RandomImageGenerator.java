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

    private final int size;
    private final Random random;
    private final DefaultDataBufferFactory dataBufferFactory;

    public RandomImageGenerator(@Value("${image.random.size:#{null}}") Integer size) {
        if (size != null && size < 0)
            throw new IllegalArgumentException();
        this.size = size != null ? size : DEFAULT_GENERATION_SIZE;
        this.random = new Random();
        this.dataBufferFactory = new DefaultDataBufferFactory();
    }

    public Mono<DataBuffer> generate(@NonNull String seed, int scaleBy) {
        if (scaleBy < 1)
            Mono.error(new IllegalArgumentException());
        return Mono.create(monoSink -> {
            try {
                var targetSize = size * scaleBy;
                var bufferedImage = new BufferedImage(size, size, BufferedImage.TYPE_4BYTE_ABGR);
                for (var i = 0; i < size / 2; i++)
                    for (var j = 0; j < size; j++)
                        if (random.nextInt(10) < 2)
                            bufferedImage.setRGB(i, j, 0xff00ff00);
                        else
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

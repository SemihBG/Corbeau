package com.semihbkgr.corbeau.component;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Random;


@Component
public class RandomImageGenerator {

    static final int DEFAULT_SIZE = 8;

    private final int size;
    private final Random random;
    private final DefaultDataBufferFactory dataBufferFactory;

    public RandomImageGenerator(@Value("${image.random.size:#{null}}") Integer size) {
        this.size = size != null ? size : DEFAULT_SIZE;
        random = new Random();
        dataBufferFactory = new DefaultDataBufferFactory();
    }

    public Mono<DataBuffer> generate(@NonNull String seed) {
        return Mono.create(monoSink -> {
            try {
                var img = new BufferedImage(size, size, BufferedImage.TYPE_4BYTE_ABGR);
                for (var i = 0; i < size / 2; i++)
                    for (var j = 0; j < size; j++)
                        if (random.nextInt(10) < 2)
                            img.setRGB(i, j, 0xff00ff00);
                        else
                            img.setRGB(i, j, 0xff000000);
                for (var i = size / 2; i < size; i++)
                    for (var j = 0; j < size; j++)
                        img.setRGB(i, j, img.getRGB(size - i - 1, j));
                var byteArrayOutputStrea = new ByteArrayOutputStream();
                ImageIO.write(img, "png", byteArrayOutputStrea);
                var dataBuffer = dataBufferFactory.allocateBuffer(size * size * Integer.BYTES);
                dataBuffer.write(byteArrayOutputStrea.toByteArray());
                monoSink.success(dataBuffer);
            } catch (Throwable t) {
                monoSink.error(t);
            }
        });
    }

}

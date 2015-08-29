package com.changhewl.hotel.mina;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author BruceYang
 *
 */
@Component("byteArrayCodecFactory")
public class ByteArrayCodecFactory implements ProtocolCodecFactory {
    @Autowired
    private ByteArrayDecoder decoder;
    @Autowired
    private ByteArrayEncoder encoder;

    @Override
    public ProtocolDecoder getDecoder(IoSession session) throws Exception {
        return decoder;
    }

    @Override
    public ProtocolEncoder getEncoder(IoSession session) throws Exception {
        return encoder;
    }

}

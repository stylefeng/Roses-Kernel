/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.stylefeng.roses.kernel.socket.websocket.server;

import cn.stylefeng.roses.kernel.socket.websocket.server.handler.WebSocketMessageHandler;
import com.gettyio.core.channel.SocketChannel;
import com.gettyio.core.pipeline.ChannelInitializer;
import com.gettyio.core.pipeline.DefaultChannelPipeline;
import com.gettyio.expansion.handler.codec.websocket.WebSocketDecoder;
import com.gettyio.expansion.handler.codec.websocket.WebSocketEncoder;

/**
 * WebSocket通道责任链对象
 *
 * @author majianguo
 * @date 2021/6/1 下午2:36
 */
public class WebSocketInitializer extends ChannelInitializer {

    @Override
    public void initChannel(SocketChannel channel) throws Exception {
        // 获取责任链对象
        DefaultChannelPipeline pipeline = channel.getDefaultChannelPipeline();

        // 先把ws的编解码器添加到责任链前面。注意，只有先通过ws的编解码器，才能解析ws的消息帧，
        // 后续的解码器才能继续解析期望得到的结果
        pipeline.addLast(new WebSocketEncoder());
        pipeline.addLast(new WebSocketDecoder());

        // 添加自定义的消息处理器
        pipeline.addLast(new WebSocketMessageHandler());

    }
}

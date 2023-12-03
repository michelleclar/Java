package org.carl.chat.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.carl.chat.message.LoginRequestMessage;
import org.carl.chat.message.LoginResponseMessage;
import org.carl.chat.server.service.UserServiceFactory;
import org.carl.chat.server.session.SessionFactory;

@ChannelHandler.Sharable
public class LoginRequestMessageHandler extends SimpleChannelInboundHandler<LoginRequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginRequestMessage loginRequestMessage) throws Exception {
        var username = loginRequestMessage.getUsername();
        var password = loginRequestMessage.getPassword();
        var login = UserServiceFactory.getUserService().login(username, password);
        LoginResponseMessage r;
        if (login) {
            SessionFactory.getSession().bind(channelHandlerContext.channel(), username);
            r = new LoginResponseMessage(true, "登录成功");
        } else {
            r = new LoginResponseMessage(false, "登录失败");
        }
        channelHandlerContext.writeAndFlush(r);
    }
}

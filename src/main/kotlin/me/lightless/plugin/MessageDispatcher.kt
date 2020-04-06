package me.lightless.plugin

import me.lightless.plugin.handler.CommandHandler
import me.lightless.plugin.handler.MessageHandler
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.utils.MiraiLogger

class MessageDispatcher {

    private final val tag = "[MessageDispatcher]"
    private final val allowedGroups = listOf<Long>(574255110, 672534169)
    private final val commandHandler = CommandHandler()
    private final val messageHandler = MessageHandler()

    init {
        println("MessageDispatcher init!")
    }

    private fun isCommand(message: String): Boolean = message.startsWith("/")

    suspend fun onMessage(group: Group, message: MessageChain) {
        IzumiPluginMain.logger.info("$tag group: $group, message: $message")
//        group.sendMessage(PlainText("$message"))
//        group.sendMessage(message)
        // 判断生效的QQ群
        if (group.id !in allowedGroups) {
            return
        }

        when {
            isCommand(message.toString()) -> {
                commandHandler.dispatcher()
            }
            else -> {
                messageHandler.dispatcher()
            }
        }

    }

}
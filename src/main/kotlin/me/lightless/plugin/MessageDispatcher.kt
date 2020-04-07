package me.lightless.plugin

import me.lightless.plugin.handler.CommandHandler
import me.lightless.plugin.handler.MessageHandler
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.message.data.MessageChain

class MessageDispatcher {

    private val tag = "[MessageDispatcher]"
    private val allowedGroups = listOf<Long>(574255110, 672534169)
    private val commandHandler = CommandHandler()
    private val messageHandler = MessageHandler()

    init {
        IzumiPluginMain.logger.info("$tag MessageDispatcher init!")
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
            isCommand(message.toString()) -> commandHandler.dispatcher()
            else -> messageHandler.dispatcher()
        }

    }

}
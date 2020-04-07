package me.lightless.plugin

import me.lightless.plugin.handler.CommandHandler
import me.lightless.plugin.handler.MessageHandler
import net.mamoe.mirai.console.plugins.Config
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.message.data.MessageChain

class MessageDispatcher(
    botConfig: Config
) {

    companion object {
        private const val TAG = "[MessageDispatcher]"
    }

    private var allowedGroups: List<Long>
    private val commandHandler = CommandHandler()
    private val messageHandler = MessageHandler()

    init {
        IzumiPluginMain.logger.info("$TAG MessageDispatcher init!")
        allowedGroups = botConfig.getLongList("enable_groups")
    }

    private fun isCommand(message: String): Boolean = message.startsWith("/")

    suspend fun onMessage(group: Group, message: MessageChain) {
        IzumiPluginMain.logger.info("$TAG group: $group, message: $message")
//        group.sendMessage(PlainText("$message"))
//        group.sendMessage(message)
        // 判断生效的QQ群
        if (group.id !in allowedGroups) {
            return
        }

//        group.sendMessage(message)

        when {
            isCommand(message.toString()) -> commandHandler.dispatcher()
            else -> messageHandler.dispatcher()
        }

    }
}
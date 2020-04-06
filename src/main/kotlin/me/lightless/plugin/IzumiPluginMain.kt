package me.lightless.plugin

import kotlinx.coroutines.launch
import net.mamoe.mirai.console.plugins.PluginBase
import net.mamoe.mirai.event.subscribeGroupMessages
import net.mamoe.mirai.event.subscribeMessages

object IzumiPluginMain : PluginBase() {

    private var messageDispatcher: MessageDispatcher? = null

    override fun onLoad() {
        // 加载本地资源
        // 加载配置文件
        // 初始化变量
        super.onLoad()
        logger.info("Plugin loaded!")

        messageDispatcher = MessageDispatcher()

    }

    override fun onEnable() {
        // 注册指令
        // 注册事件监听
        // 启动插件中的循环任务，例如协程或者worker
        super.onEnable()

        logger.info("Plugin enable!")

        subscribeMessages {
//            "greeting" reply { "Hello ${sender.nick}" }
        }
//
//        subscribeAlways<MessageRecallEvent> { event ->
//            logger.info { "${event.authorId} 的消息被撤回了" }
//        }

        subscribeGroupMessages {
            always {
                launch {
                    messageDispatcher?.onMessage(group, message)
                }
            }
        }

    }
}
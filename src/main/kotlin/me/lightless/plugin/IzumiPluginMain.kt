package me.lightless.plugin

import kotlinx.coroutines.launch
import net.mamoe.mirai.console.plugins.Config
import net.mamoe.mirai.console.plugins.PluginBase
import net.mamoe.mirai.console.plugins.withDefaultWriteSave
import net.mamoe.mirai.event.subscribeGroupMessages
import net.mamoe.mirai.event.subscribeMessages

object IzumiPluginMain : PluginBase() {

    private const val TAG = "[IzumiPluginMain]"
    private const val CONFIG_FILENAME = "config.yml"

    private var messageDispatcher: MessageDispatcher? = null
    private lateinit var botConfig: Config

    override fun onLoad() {
        // 加载本地资源
        // 加载配置文件
        // 初始化变量
        super.onLoad()
        logger.info("$TAG Plugin loaded!")

        // 加载配置文件
        logger.info("$TAG start loading config file...")
        try {
            botConfig = loadConfig(CONFIG_FILENAME)
        } catch (e: Exception) {
            e.printStackTrace()
            logger.error("$TAG 无法加载配置文件 $CONFIG_FILENAME")
            return
        }

        // 初始化配置文件
        try {
            val enableGroups = botConfig.getLongList("enable_groups")
            if (enableGroups.isEmpty()) {
                logger.error("$TAG 请在配置文件 $CONFIG_FILENAME 中配置 enable_groups")
                return
            }
            logger.info("$TAG enable_groups: $enableGroups")
        } catch (e: Exception) {
            botConfig["enable_groups"] = listOf(0, 0)
            botConfig.save()
            logger.error("$TAG 请在配置文件 $CONFIG_FILENAME 中配置 enable_groups")
            return
        }

        // 初始化变量
        messageDispatcher = MessageDispatcher(botConfig)
    }

    override fun onEnable() {
        // 注册指令
        // 注册事件监听
        // 启动插件中的循环任务，例如协程或者worker
        super.onEnable()

        logger.info("$TAG Plugin enable!")

//        subscribeMessages {
//            "greeting" reply { "Hello ${sender.nick}" }
//        }
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
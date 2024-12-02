package com.bez.newsfeedtabs.domain.usecase

import com.bez.newsfeedtabs.data.repo.EntertainmentRepoPart1
import com.bez.newsfeedtabs.data.repo.EntertainmentRepoPart2
import com.bez.newsfeedtabs.domain.model.NewsItem
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

class FetchEntertainmentUC @Inject constructor(
    private val entertainmentRepoPart1: EntertainmentRepoPart1,
    private val entertainmentRepoPart2: EntertainmentRepoPart2
) {

    private val mutex = Mutex() // Ensure thread-safe updates

    fun fetchEntertainmentNews(): Flow<List<NewsItem>> = channelFlow {
        val itemsList = mutableListOf<NewsItem>()

        launch {
            try {
                withTimeout(3000) {  // Timeout after x ms
                    val part1 = entertainmentRepoPart1.fetchEntertainmentNewsPart1()
                    mutex.withLock {

//                        delay(1500) //for testing purposes

                        itemsList.addAll(0, part1) // Add Part 1 to the start of the list
                        send(itemsList.toList()) // Send updated list
                    }
                }
            } catch (e: TimeoutCancellationException) {
//                throw Exception("Fetching Part 1 timed out", e)
            } catch (e: Exception) {
                throw Exception("Error fetching Part 1: ${e.localizedMessage}", e)
            }
        }

        launch {
            try {
                withTimeout(3000) {  // Timeout after x ms
                    val part2 = entertainmentRepoPart2.fetchEntertainmentNewsPart2()
                    mutex.withLock {

//                        delay(500) //for testing purposes

                        itemsList.addAll(part2) // Add Part 2 to the end of the list
                        send(itemsList.toList()) // Send updated list
                    }
                }
            } catch (e: TimeoutCancellationException) {
//                throw Exception("Fetching Part 2 timed out", e)
            } catch (e: Exception) {
                throw Exception("Error fetching Part 2: ${e.localizedMessage}", e)
            }
        }
    }
}

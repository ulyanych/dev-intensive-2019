package ru.skillbranch.devintensive.models

import android.os.Bundle
import android.util.Log

class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {

    fun askQuestion(): String = when (question){
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> {
        val error = question.validateAnswer(answer)
        if (error != null) {
            return "$error\n${question.question}" to status.color
        }
        return  if (question == Question.IDLE) {
            question.question to status.color
        } else if (question.answers.contains(answer.toLowerCase())) {
            question = question.nextQuestion()
            "Отлично - ты справился\n${question.question}" to status.color
        } else {
            if (status == Status.CRITICAL) {
                status = Status.NORMAL
                question = Question.NAME
                return "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
            }
            status = status.nextStatus()
            "Это неправильный ответ\n${question.question}" to status.color
        }
    }

    fun saveState(outState: Bundle) {
        outState.putString("STATUS", status.name)
        outState.putString("QUESTION", question.name)

        Log.d("M_MainActivity:", "onSaveInstanceState ${status.name} ${question.name}")
    }

    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)) ,
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0));

        fun nextStatus(): Status {
            return if(this.ordinal < values().lastIndex) {
                values()[this.ordinal + 1]
            } else {
                values()[0]
            }
        }
    }

    enum class Question(val question: String, val answers: List<String>) {
        NAME("Как меня зовут?", listOf("бендер", "bender")) {
            override fun nextQuestion(): Question = PROFESSION

            override fun validateAnswer(answer: String): String? {
                val regex = "^[A-ZА-Я].*".toRegex()
                if (!answer.matches(regex)) {
                    return "Имя должно начинаться с заглавной буквы"
                }
                return null
            }
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun nextQuestion(): Question = MATERIAL

            override fun validateAnswer(answer: String): String? {
                val regex = "^[a-zа-я].*".toRegex()
                if (!answer.matches(regex)) {
                    return "Профессия должна начинаться со строчной буквы"
                }
                return null
            }
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")) {
            override fun nextQuestion(): Question = BDAY

            override fun validateAnswer(answer: String): String? {
                val regex = "^[^0-9]+$".toRegex()
                if (!answer.matches(regex)) {
                    return "Материал не должен содержать цифр"
                }
                return null
            }
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun nextQuestion(): Question = SERIAL

            override fun validateAnswer(answer: String): String? {
                val regex = "^[0-9]+$".toRegex()
                if (!answer.matches(regex)) {
                    return "Год моего рождения должен содержать только цифры"
                }
                return null
            }
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun nextQuestion(): Question = IDLE

            override fun validateAnswer(answer: String): String? {
                val regex = "^[0-9]{7}$".toRegex()
                if (!answer.matches(regex)) {
                    return "Серийный номер содержит только цифры, и их 7"
                }
                return null
            }
        },
        IDLE("На этом все, вопросов больше нет", listOf()) {
            override fun nextQuestion(): Question = IDLE

            override fun validateAnswer(answer: String): String? {
                return null
            }
        };

        abstract fun nextQuestion(): Question
        abstract fun validateAnswer(answer: String): String?
    }
}
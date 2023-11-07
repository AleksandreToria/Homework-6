package com.example.homework6

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import com.example.homework6.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var info: Array<AppCompatEditText>

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val users = Users()

        info = arrayOf(
            binding.firstName,
            binding.lastName,
            binding.email,
            binding.age
        )

        binding.addBtn.setOnClickListener {
            binding.status.text = ""

            if (areElementsNullOrBlank(info)) {
                Toast.makeText(this, "Please don't leave fields empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val firstName = info[0].text.toString()
            val lastName = info[1].text.toString()
            val email = info[2].text.toString()
            val age= info[3].text.toString().toInt()

            if (!isEmailValid(email)) {
                Toast.makeText(this, "Please enter valid email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (users.userExists(email)) {
                binding.status.text = "User already exists"
                binding.status.setBackgroundColor(Color.RED)
                return@setOnClickListener
            }


            val userInformation = UserInformation(firstName, lastName, email, age)
            users.addUser(userInformation)

            binding.firstName.text?.clear()
            binding.lastName.text?.clear()
            binding.email.text?.clear()
            binding.age.text?.clear()

            val usersList = users.userCount()
            binding.userCount.text = "User count: $usersList"
            binding.status.text = "User added successfully"
            binding.status.setBackgroundColor(Color.GREEN)
        }

        binding.removeBtn.setOnClickListener {
            binding.status.text = ""

            val firstName = info[0].text.toString()
            val lastName = info[1].text.toString()
            val email = info[2].text.toString()
            val ageText = info[3].text.toString()

            if (!users.userExists(email)) {
                binding.status.text = "User does not exist"
                binding.status.setBackgroundColor(Color.RED)
                return@setOnClickListener
            }

            val age = if (ageText.isNotEmpty()) {
                ageText.toInt()
            }else {
                0
            }

            val userToRemove = UserInformation(firstName, lastName, email, age)
            users.removeUser(userToRemove)
            binding.userCount.text = users.userCount().toString()

            binding.firstName.text?.clear()
            binding.lastName.text?.clear()
            binding.email.text?.clear()
            binding.age.text?.clear()

            val usersList = users.userCount()
            binding.userCount.text = "User count: $usersList"
            binding.usersRemoved.text = "Users removed: ${users.usersRemoved()}"
            binding.status.text = "User deleted successfully"
            binding.status.setBackgroundColor(Color.GREEN)
        }

        binding.updateBtn.setOnClickListener {
            binding.status.text = ""

            val firstName = info[0].text.toString()
            val lastName = info[1].text.toString()
            val email = info[2].text.toString()
            val ageText = info[3].text.toString()

            if (!users.userExists(email)) {
                binding.status.text = "User does not exist"
                binding.status.setBackgroundColor(Color.RED)
                return@setOnClickListener
            }

            binding.email.visibility = View.GONE
            binding.addBtn.visibility = View.GONE
            binding.removeBtn.visibility = View.GONE
            binding.status.visibility = View.GONE

            val age = if (ageText.isNotEmpty()) {
                ageText.toInt()
            }else {
                0
            }

            val user = UserInformation(firstName, lastName, email, age)

            binding.updateBtn.setOnClickListener {
                users.update(user)

                binding.email.visibility = View.VISIBLE
                binding.addBtn.visibility = View.VISIBLE
                binding.removeBtn.visibility = View.VISIBLE
                binding.status.visibility = View.VISIBLE

                binding.status.text = "Successfully updated"
                binding.status.setBackgroundColor(Color.GREEN)
            }
        }
    }

    private fun areElementsNullOrBlank(inputs: Array<AppCompatEditText>): Boolean {
        return inputs.any { input -> input.text.isNullOrBlank() }
    }

    private fun isEmailValid(mail: String): Boolean {
        val trimmedMail = mail.trim()
        return trimmedMail.contains("@") && !trimmedMail.startsWith("@") && !trimmedMail.endsWith("@")
    }
}
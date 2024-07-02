#!/bin/bash

# Step 1: Get the instance id or name from us-east-1
instance_info=$(aws ec2 describe-instances --region us-east-1 --query 'Reservations[].Instances[].{ID:InstanceId, Name:Tags[?Key==`Name`].Value}' --output json)

# Extracting instance ID and name
instance_id=$(echo "$instance_info" | jq -r '.[].ID')
instance_name=$(echo "$instance_info" | jq -r '.[].Name')

# Step 2: Get the public IP address of the EC2 instance
public_ip=$(aws ec2 describe-instances --instance-ids "$instance_id" --region us-east-1 --query 'Reservations[].Instances[].PublicIpAddress' --output json | jq -r '.[]')

# Step 3: Print the public IP address
echo "Instance Name: $instance_name"
echo "Instance ID: $instance_id"
echo "Public IP Address: $public_ip"

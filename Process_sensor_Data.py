import csv
import os
from collections import defaultdict

def read_csv_files(folder_path):
    result = defaultdict(list)
    for filename in os.listdir(folder_path):
        if filename.endswith(".csv"):
            with open(os.path.join(folder_path, filename), "r") as f:
                reader = csv.reader(f)
                header = next(reader)  # Skip the header row
                for row in reader:
                    sensor = row[0]
                    salary = float(row[1])
                    result[sensor].append(salary)
    return result

def calculate_statistics(data):
    result = {}
    for sensor, humidity in data.items():
        avg = sum(humidity) / len(humidity)
        min_salary = min(humidity)
        max_salary = max(humidity)
        result[sensor] = (min_salary,avg, max_salary)
    return result

if __name__ == "__main__":
    folder_path = "C:\\Users\\nagar\\OneDrive\\Documents\\Veeresh\\dataset\\dataset\\sensor_datafiles"
    data = read_csv_files(folder_path)
    stats = calculate_statistics(data)
    for keys,values in stats.items():
        print(keys,values,sep=" ")
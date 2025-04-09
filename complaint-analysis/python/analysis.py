import pandas as pd
import json
import sys
from collections import Counter

def analyze_complaints(input_json):
    """Analyze complaint data and return insights"""
    df = pd.read_json(input_json)
    
    # Category analysis
    category_counts = dict(Counter(df['category']))
    
    # Trend analysis (by month)
    df['month'] = pd.to_datetime(df['createdAt']).dt.to_period('M')
    trend_counts = dict(Counter(df['month'].astype(str)))
    
    return {
        'categories': category_counts,
        'trends': trend_counts
    }

if __name__ == "__main__":
    # Read input from stdin when called from Java
    input_data = sys.stdin.read()
    result = analyze_complaints(input_data)
    print(json.dumps(result))
